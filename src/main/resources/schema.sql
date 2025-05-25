drop table if exists Follow;
drop table if exists Save;
drop table if exists Review;
drop table if exists Ingredient;
drop table if exists Recipe;
drop table if exists Profile;
drop table if exists FeaturedImage;
drop table if exists Account;

create table if not exists Account(
    id char(36) not null,
    username varchar(32) unique not null,
    password varchar(64) not null,
    role varchar(64) not null,
    primary key (id)
);

create table if not exists Profile(
    accountId char(36) not null,
    firstName varchar(255) not null,
    lastName varchar(255) not null,
    email varchar(255) unique not null,
    description text,
    createdAt date not null,
    primary key (accountId),
    foreign key (accountId) references Account(id)
);

create table if not exists FeaturedImage(
    id char(36) not null,
    uploadDate date not null,
    data mediumblob not null,
    fileName varchar(255) not null,
    primary key (id)
);

create table if not exists Recipe(
    id char(36) not null,
    title varchar(255) not null,
    accountId char(36) not null,
    featuredImageId char(36),
    createdAt date not null,
    description text,
    rating numeric,
    completionTimeInMinutes numeric,
    numServings numeric,
    ingredients text,
    instructions text,
    primary key (id),
    foreign key (accountId) references Account(id),
    foreign key (featuredImageId) references FeaturedImage(id)
);

create table if not exists Save(
    accountId char(36) not null,
    recipeId char(36) not null,
    createdAt date not null,
    primary key (accountId, recipeId),
    foreign key (accountId) references Account(id),
    foreign key (recipeId) references Recipe(id)
);

create table if not exists Review(
    id char(36) not null,
    accountId char(36) not null,
    recipeId char(36) not null,
    score numeric not null,
    description text not null,
    createdAt date not null,
    primary key (id),
    foreign key (accountId) references Account(id),
    foreign key (recipeId) references Recipe(id)
);

create table if not exists Follow(
    accountId char(36) not null,
    followerId char(36) not null,
    primary key (accountId, followerId),
    foreign key (accountId) references Account(id),
    foreign key (followerId) references Account(id)
);

create table if not exists Ingredient(
    id char(36) not null,
    accountId char(36) not null,
    recipeId char(36) not null,
    name varchar(255) not null,
    createdAt date not null,
    primary key (id),
    foreign key (accountId) references Account(id),
    foreign key (recipeId) references Recipe(id)
);

create or replace view ProfileSummary as
select
    a.username as username, p.firstName as firstName, p.lastName as lastName,
    p.createdAt as joinDate, coalesce(p.description, 'No description available') as description,
    count(r.id) as numRecipes
from Account a
join Profile p on a.id = p.accountId
left join Recipe r on r.accountId = a.id
group by a.username, p.firstName, p.lastName, p.createdAt, p.description;

create or replace view RecipePreview as
select
    r.id, r.title, r.featuredImageId, r.description, r.rating,
    a.username,
    p.firstName, p.lastName
from Account a, Recipe r, Profile p
where a.id = p.accountId and r.accountId = a.id;

drop trigger if exists after_review_insert;

create trigger after_review_insert
after insert on Review
for each row
    update Recipe
    set rating = (
        select avg(score)
        from Review
        where recipeId = new.recipeId
    )
    where id = new.recipeId;

drop procedure if exists average_rating;

create procedure average_rating(in username varchar(255), out user_average_rating numeric)
    select avg(r.score) into user_average_rating
    from Review r, Recipe rcp, Account a
    where r.recipeId = rcp.id and rcp.accountId = a.id and a.username = username;

drop procedure if exists get_recipes_under_half_hour;

create procedure get_recipes_under_half_hour()
    select id, title, completionTimeInMinutes
    from Recipe
    where completionTimeInMinutes < 30;

--create function get_username(accountId char(36))
--returns varchar(255)
--deterministic
--begin
--declare result varchar(255);
--    select username into result
--    from Account
--    where id = accountId;
--    return result;
--end;