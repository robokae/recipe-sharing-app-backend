create table if not exists Account(
    id char(36) not null,
    username varchar(32) unique not null,
    password varchar(64) not null,
    role varchar(64) not null,
    primary key (id)
);

create table if not exists Profile(
    id char(36) not null,
    accountId char(36) not null,
    firstName varchar(255) not null,
    lastName varchar(255) not null,
    email varchar(255) unique not null,
    description text,
    createdAt date not null,
    primary key (id),
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