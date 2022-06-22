CREATE TABLE IF NOT EXISTS cart
(
    id integer NOT NULL,
    comment varchar(255) COLLATE pg_catalog."default" NOT NULL,
    current boolean NOT NULL,
    overall_price double precision NOT NULL,
    customer_id integer NOT NULL,
    CONSTRAINT cart_pkey PRIMARY KEY (id),
    CONSTRAINT fkrynrwuqbpdheocivcmp9itsxi FOREIGN KEY (customer_id)
    REFERENCES users (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION,
    CONSTRAINT cart_overall_price_check CHECK (overall_price >= 0::double precision)
    )

CREATE TABLE IF NOT EXISTS chosen_meal
(
    id integer NOT NULL,
    amount integer NOT NULL,
    final_price double precision NOT NULL,
    img varchar(255) COLLATE pg_catalog."default" NOT NULL,
    meal_price double precision NOT NULL,
    cart_id integer NOT NULL,
    meal_id integer NOT NULL,
    CONSTRAINT chosen_meal_pkey PRIMARY KEY (id),
    CONSTRAINT fk351w73cnkiwn02b267mh9b9am FOREIGN KEY (cart_id)
    REFERENCES cart (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION,
    CONSTRAINT fk76osc4ci6w9yyyvcm81imvswx FOREIGN KEY (meal_id)
    REFERENCES meal (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION,
    CONSTRAINT chosen_meal_amount_check CHECK (amount >= 1),
    CONSTRAINT chosen_meal_final_price_check CHECK (final_price >= 0::double precision),
    CONSTRAINT chosen_meal_meal_price_check CHECK (meal_price >= 0::double precision)
    )

CREATE TABLE IF NOT EXISTS cart_chosen_meals
(
    cart_id integer NOT NULL,
    chosen_meals_id integer NOT NULL,
    CONSTRAINT uk_4d4cqw8gxol7nt364rgteufpn UNIQUE (chosen_meals_id),
    CONSTRAINT fk7b3jlg0cml3lk9qm708b36eis FOREIGN KEY (cart_id)
    REFERENCES cart (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION,
    CONSTRAINT fkey1bfvui2c58ujltf2t404f9q FOREIGN KEY (chosen_meals_id)
    REFERENCES chosen_meal (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    )

CREATE TABLE IF NOT EXISTS chef
(
    id integer NOT NULL,
    adding_date timestamp without time zone NOT NULL,
    description varchar(255) COLLATE pg_catalog."default" NOT NULL,
    img varchar(255) COLLATE pg_catalog."default" NOT NULL,
    name varchar(255) COLLATE pg_catalog."default" NOT NULL,
    views integer NOT NULL,
    CONSTRAINT chef_pkey PRIMARY KEY (id),
    CONSTRAINT chef_views_check CHECK (views >= 0)
    )

CREATE TABLE IF NOT EXISTS chef_restaurants
(
    chef_id integer NOT NULL,
    restaurants_id integer NOT NULL,
    CONSTRAINT uk_n4paneofqi60s2ab2yyr6eyej UNIQUE (restaurants_id),
    CONSTRAINT fk83x7sggg4pxxlj7dk9bv25vxh FOREIGN KEY (chef_id)
    REFERENCES chef (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION,
    CONSTRAINT fki4b6ncscpnj1j107lamet6cdf FOREIGN KEY (restaurants_id)
    REFERENCES restaurant (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    )

CREATE TABLE IF NOT EXISTS choice
(
    id integer NOT NULL,
    max_choices integer,
    min_choices integer,
    title varchar(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT choice_pkey PRIMARY KEY (id),
    CONSTRAINT choice_max_choices_check CHECK (max_choices >= 1),
    CONSTRAINT choice_min_choices_check CHECK (min_choices >= 0)
    )

CREATE TABLE IF NOT EXISTS choice_options
(
    choice_id integer NOT NULL,
    options_id integer NOT NULL,
    CONSTRAINT uk_1t1et3evihlph21ctdkw5ri23 UNIQUE (options_id),
    CONSTRAINT fkev33efykkeuqwkaku5tsxagkf FOREIGN KEY (choice_id)
    REFERENCES choice (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION,
    CONSTRAINT fkt3nwtytlxp8bj4dwj52njysn3 FOREIGN KEY (options_id)
    REFERENCES option (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    )

CREATE TABLE IF NOT EXISTS chosen_meal_chosen_options
(
    chosen_meal_id integer NOT NULL,
    chosen_options_id integer NOT NULL,
    CONSTRAINT fki7mtq1sbd2huroa02tmooj29n FOREIGN KEY (chosen_meal_id)
    REFERENCES chosen_meal (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION,
    CONSTRAINT fkj0yb3pnd6umgqbrr2wmaqm308 FOREIGN KEY (chosen_options_id)
    REFERENCES option (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    )

CREATE TABLE IF NOT EXISTS login_attempt
(
    id integer NOT NULL,
    mail varchar(255) COLLATE pg_catalog."default" NOT NULL,
    "time" timestamp without time zone NOT NULL,
    CONSTRAINT login_attempt_pkey PRIMARY KEY (id)
    )

CREATE TABLE IF NOT EXISTS meal
(
    id integer NOT NULL,
    category varchar(255) COLLATE pg_catalog."default" NOT NULL,
    description varchar(255) COLLATE pg_catalog."default",
    gluten_free boolean NOT NULL,
    img varchar(255) COLLATE pg_catalog."default" NOT NULL,
    name varchar(255) COLLATE pg_catalog."default" NOT NULL,
    price double precision NOT NULL,
    signature boolean NOT NULL,
    spicy boolean NOT NULL,
    vegan boolean NOT NULL,
    CONSTRAINT meal_pkey PRIMARY KEY (id),
    CONSTRAINT meal_price_check CHECK (price >= 0::double precision)
    )

CREATE TABLE IF NOT EXISTS meal_choices
(
    meal_id integer NOT NULL,
    choices_id integer NOT NULL,
    CONSTRAINT uk_5ju7h84yr1x1mhb3iptbm5217 UNIQUE (choices_id),
    CONSTRAINT fk8r27t0ogmn39onukuhn3jg5cy FOREIGN KEY (choices_id)
    REFERENCES choice (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION,
    CONSTRAINT fkghru2cs4l111vpvnujgdj4obq FOREIGN KEY (meal_id)
    REFERENCES meal (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    )

CREATE TABLE IF NOT EXISTS option
(
    id integer NOT NULL,
    option varchar(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT option_pkey PRIMARY KEY (id)
    )

CREATE TABLE IF NOT EXISTS permit
(
    user_type integer NOT NULL,
    permissions varchar(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT permit_pkey PRIMARY KEY (user_type)
    )

CREATE TABLE IF NOT EXISTS restaurant
(
    id integer NOT NULL,
    creation timestamp without time zone NOT NULL,
    img varchar(255) NOT NULL,
    latitude double precision NOT NULL,
    longitude double precision NOT NULL,
    name varchar(255) NOT NULL,
    open boolean NOT NULL,
    popularity integer NOT NULL,
    price integer NOT NULL,
    rating integer NOT NULL,
    chef_id integer,
    CONSTRAINT restaurant_pkey PRIMARY KEY (id),
    CONSTRAINT uk_i6u3x7opncroyhd755ejknses UNIQUE (name),
    CONSTRAINT fknf0nv8cmsncnlqd0jo56ydlxv FOREIGN KEY (chef_id)
    REFERENCES chef (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION,
    CONSTRAINT restaurant_price_check CHECK (price <= 5 AND price >= 1),
    CONSTRAINT restaurant_rating_check CHECK (rating <= 5 AND rating >= 0)
    )

CREATE TABLE IF NOT EXISTS restaurant_meals
(
    restaurant_id integer NOT NULL,
    meals_id integer NOT NULL,
    CONSTRAINT uk_m090c5g9cc3asac62f2qu6iv9 UNIQUE (meals_id),
    CONSTRAINT fk3smklwua0fjn906yxo2dc7pj3 FOREIGN KEY (meals_id)
    REFERENCES meal (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION,
    CONSTRAINT fkn4dkb485yk0e98eldlcintr6o FOREIGN KEY (restaurant_id)
    REFERENCES restaurant (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    )

CREATE TABLE IF NOT EXISTS users
(
    id integer NOT NULL,
    email varchar(255) NOT NULL,
    name varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    user_type varchar(255),
    CONSTRAINT users_pkey PRIMARY KEY (id)
    )
