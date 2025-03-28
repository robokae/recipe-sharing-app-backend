create table if not exists Account(
    id int not null,
    firstName varchar(255) not null,
    lastName varchar(255) not null,
    username varchar(32) unique not null,
    password varchar(64) not null,
    email varchar(255) unique not null,
    description varchar(255),
    primary key (id)
);

create table if not exists FeaturedImage(
    id int not null,
    uploadDate date not null,
    data blob not null,
    fileName varchar(255) not null,
    primary key (id)
);

create table if not exists Recipe(
    id int not null,
    title varchar(255) not null,
    accountId int not null,
    featuredImageId int,
    createdAt date not null,
    description varchar(255),
    rating numeric,
    completionTimeInMinutes numeric,
    numServings numeric,
    ingredients varchar(255),
    instructions varchar(255),
    primary key (id),
    foreign key (accountId) references Account(id),
    foreign key (featuredImageId) references FeaturedImage(id)
);

create table if not exists Save(
    id int not null,
    accountId int not null,
    recipeId int not null,
    createdAt date not null,
    primary key (accountId, recipeId),
    foreign key (accountId) references Account(id),
    foreign key (recipeId) references Recipe(id)
);

create table if not exists Review(
    id int not null,
    accountId int not null,
    recipeId int not null,
    score numeric not null,
    description varchar(1024) not null,
    createdAt date not null,
    primary key (id),
    foreign key (accountId) references Account(id),
    foreign key (recipeId) references Recipe(id)
);

create table if not exists Follow(
    id int not null,
    accountId int not null,
    followerId int not null,
    primary key (accountId, followerId),
    foreign key (accountId) references Account(id),
    foreign key (followerId) references Account(id)
);

create table if not exists Ingredient(
    id int not null,
    accountId int not null,
    recipeId int not null,
    name varchar(255) not null,
    createdAt date not null,
    primary key (accountId, recipeId),
    foreign key (accountId) references Account(id),
    foreign key (recipeId) references Recipe(id)
);