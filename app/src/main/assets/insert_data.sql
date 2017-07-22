INSERT INTO Supplier (SupplierName, SupplierAddress, SupplierEmail, SupplierPhone) VALUES ('Ace Meats Ltd.','13 Road Drive, Sandyland, Co. Dublin','supplies@acemeats.com','01-236-9985');
INSERT INTO Supplier (SupplierName, SupplierAddress, SupplierEmail, SupplierPhone) VALUES ('McFallon Fruit Supplies','19 Grange Terrace, Farmtown, Co. Dublin','orders@mfss.ie','01-295-7386');
INSERT INTO Supplier (SupplierName, SupplierAddress, SupplierEmail, SupplierPhone) VALUES ('Grange Dairies','22A Parkway, Cintertown, Co. Dublin','suppliers@grangedairies.com','01-240-6325');
INSERT INTO Supplier (SupplierName, SupplierAddress, SupplierEmail, SupplierPhone) VALUES ('Fresh Farms Inc.','Building 4, Citadel Business Park, Chipstown, Co. Dublin','inquiries@ffi.ie','01-236-9986');
INSERT INTO Supplier (SupplierName, SupplierAddress, SupplierEmail, SupplierPhone) VALUES ('Sulvihill Foods Ltd.','Unit 7, Langstown Business Centre, Grangeland, Co. Dublin','orders@sulbihill.ie','01-296-1120');
INSERT INTO Supplier (SupplierName, SupplierAddress, SupplierEmail, SupplierPhone) VALUES ('Baker''s Pantry','32 Tent Street, Tindale, Co. Dublin','supply@bakerspantry.com','01-395-7351');
INSERT INTO Supplier (SupplierName, SupplierAddress, SupplierEmail, SupplierPhone) VALUES ('Parvo International','Unit 100-105, Langstown Business Centre, Grangeland, Co. Dublin','ieorders@parvoint.com','01-136-1230');
INSERT INTO Supplier (SupplierName, SupplierAddress, SupplierEmail, SupplierPhone) VALUES ('Citcon Food Suppliers','Building 9, Citadel Business Park, Chipstown, Co. Dublin','foodsupplies@citcon.com','01-295-7389');

INSERT INTO Category (CategoryName, IconID) VALUES ('Bakery','bakery');
INSERT INTO Category (CategoryName, IconID) VALUES ('Cereals & Dried Foods','cereal');
INSERT INTO Category (CategoryName, IconID) VALUES ('Dairy, Milk & Eggs','dairy');
INSERT INTO Category (CategoryName, IconID) VALUES ('Deli','deli');
INSERT INTO Category (CategoryName, IconID) VALUES ('Drinks','drinks');
INSERT INTO Category (CategoryName, IconID) VALUES ('Frozen Foods','freezer');
INSERT INTO Category (CategoryName, IconID) VALUES ('Fruit & Vegetables','butcher');
INSERT INTO Category (CategoryName, IconID) VALUES ('Herbs & Spices','butcher');
INSERT INTO Category (CategoryName, IconID) VALUES ('Home Baking','baking');
INSERT INTO Category (CategoryName, IconID) VALUES ('Meat & Poultry','butcher');
INSERT INTO Category (CategoryName, IconID) VALUES ('Nuts, Seeds & Dried Fruit','nuts');
INSERT INTO Category (CategoryName, IconID) VALUES ('Oils','oils');
INSERT INTO Category (CategoryName, IconID) VALUES ('Seafood','seafood');
INSERT INTO Category (CategoryName, IconID) VALUES ('Sweets & Snacks','sweets');
INSERT INTO Category (CategoryName, IconID) VALUES ('Vitamins & Supplements','vitamin');

INSERT INTO Product (ProductName, CategoryID, SalePrice, Quantity, QuantityUnit, SupplierID, PicID) VALUES ('Red Apples',7,2.41433,18,'kg',2,'apple_red');
INSERT INTO Product (ProductName, CategoryID, SalePrice, Quantity, QuantityUnit, SupplierID, PicID) VALUES ('Bannanas',7,2.30918,14,'kg',2,'banana');
INSERT INTO Product (ProductName, CategoryID, SalePrice, Quantity, QuantityUnit, SupplierID, PicID) VALUES ('Broccoli',7,3.91355,18,'kg',4,'broccoli');
INSERT INTO Product (ProductName, CategoryID, SalePrice, Quantity, QuantityUnit, SupplierID, PicID) VALUES ('McCambridges Brown Soda Bread (500g)',1,1.91803,27,'pack(s)',6,'brown_soda_bread_mccambridge_500g');
INSERT INTO Product (ProductName, CategoryID, SalePrice, Quantity, QuantityUnit, SupplierID, PicID) VALUES ('Red Cabbage',7,4.16099,22,'kg',4,'cabbage_red');
INSERT INTO Product (ProductName, CategoryID, SalePrice, Quantity, QuantityUnit, SupplierID, PicID) VALUES ('Carrots',7,3.39256,25,'kg',4,'carrots');
INSERT INTO Product (ProductName, CategoryID, SalePrice, Quantity, QuantityUnit, SupplierID, PicID) VALUES ('Cauliflower',7,4.14038,21,'kg',4,'cauliflower');
INSERT INTO Product (ProductName, CategoryID, SalePrice, Quantity, QuantityUnit, SupplierID, PicID) VALUES ('Courgette',7,2.88986,28,'kg',5,'courgette');
INSERT INTO Product (ProductName, CategoryID, SalePrice, Quantity, QuantityUnit, SupplierID, PicID) VALUES ('Irish Pride Farmhouse Soda Bread (460g)',1,3.56014,23,'pack(s)',6,'farmhouse_soda_irish_pride_460g');
INSERT INTO Product (ProductName, CategoryID, SalePrice, Quantity, QuantityUnit, SupplierID, PicID) VALUES ('Red Grapefruit',7,4.53849,8,'kg',7,'grapefruit_red');
INSERT INTO Product (ProductName, CategoryID, SalePrice, Quantity, QuantityUnit, SupplierID, PicID) VALUES ('Leeks',7,4.60186,22,'kg',5,'leeks');
INSERT INTO Product (ProductName, CategoryID, SalePrice, Quantity, QuantityUnit, SupplierID, PicID) VALUES ('Lemons',7,3.56595,30,'kg',4,'lemons');
INSERT INTO Product (ProductName, CategoryID, SalePrice, Quantity, QuantityUnit, SupplierID, PicID) VALUES ('Limes',7,3.73723,10,'kg',7,'limes');
INSERT INTO Product (ProductName, CategoryID, SalePrice, Quantity, QuantityUnit, SupplierID, PicID) VALUES ('Cantaloupe Melons',7,3.53782,23,'kg',4,'melon_cantaloupe');
INSERT INTO Product (ProductName, CategoryID, SalePrice, Quantity, QuantityUnit, SupplierID, PicID) VALUES ('Gala Melons',7,4.04536,17,'kg',5,'melon_gala');
INSERT INTO Product (ProductName, CategoryID, SalePrice, Quantity, QuantityUnit, SupplierID, PicID) VALUES ('Yellow Melons',7,3.23108,24,'kg',7,'melon_yellow');
INSERT INTO Product (ProductName, CategoryID, SalePrice, Quantity, QuantityUnit, SupplierID, PicID) VALUES ('Pat''s Multiseed Bread (700g)',1,4.6498,2,'pack(s)',4,'multiseed_bread_700g');
INSERT INTO Product (ProductName, CategoryID, SalePrice, Quantity, QuantityUnit, SupplierID, PicID) VALUES ('Red Onions',7,4.5912,4,'kg',4,'onion_red');
INSERT INTO Product (ProductName, CategoryID, SalePrice, Quantity, QuantityUnit, SupplierID, PicID) VALUES ('White Onions',7,3.4445,26,'kg',5,'onion_white');
INSERT INTO Product (ProductName, CategoryID, SalePrice, Quantity, QuantityUnit, SupplierID, PicID) VALUES ('Oranges',7,2.87735,16,'kg',4,'orange');
INSERT INTO Product (ProductName, CategoryID, SalePrice, Quantity, QuantityUnit, SupplierID, PicID) VALUES ('Parsnips',7,2.58081,2,'kg',5,'parsnips');
INSERT INTO Product (ProductName, CategoryID, SalePrice, Quantity, QuantityUnit, SupplierID, PicID) VALUES ('Pears',7,2.18509,29,'kg',4,'pear');
INSERT INTO Product (ProductName, CategoryID, SalePrice, Quantity, QuantityUnit, SupplierID, PicID) VALUES ('Green Peppers',7,3.61893,21,'kg',5,'pepper_green');
INSERT INTO Product (ProductName, CategoryID, SalePrice, Quantity, QuantityUnit, SupplierID, PicID) VALUES ('Red Peppers',7,4.74753,19,'kg',4,'pepper_red');
INSERT INTO Product (ProductName, CategoryID, SalePrice, Quantity, QuantityUnit, SupplierID, PicID) VALUES ('Yellow Peppers',7,2.07835,18,'kg',5,'pepper_yellow');
INSERT INTO Product (ProductName, CategoryID, SalePrice, Quantity, QuantityUnit, SupplierID, PicID) VALUES ('Rhubarb',7,3.86741,21,'kg',8,'rhubarb');
INSERT INTO Product (ProductName, CategoryID, SalePrice, Quantity, QuantityUnit, SupplierID, PicID) VALUES ('Turnips',7,2.62251,6,'kg',8,'turnip');
INSERT INTO Product (ProductName, CategoryID, SalePrice, Quantity, QuantityUnit, SupplierID, PicID) VALUES ('McCambridges Spelt Bread (500g)',1,4.78383,15,'pack(s)',6,'spelt_bread_mccambridges_500g');
INSERT INTO Product (ProductName, CategoryID, SalePrice, Quantity, QuantityUnit, SupplierID, PicID) VALUES ('Round Roast Beef',10,5.99,26,'kg',8,'beef_round_roast');
INSERT INTO Product (ProductName, CategoryID, SalePrice, Quantity, QuantityUnit, SupplierID, PicID) VALUES ('Rib Eye Steak',10,25,16,'kg',1,'steak_rib_eye');
INSERT INTO Product (ProductName, CategoryID, SalePrice, Quantity, QuantityUnit, SupplierID, PicID) VALUES ('Striploin Steak',10,22,6,'kg',1,'steak_striploin');
INSERT INTO Product (ProductName, CategoryID, SalePrice, Quantity, QuantityUnit, SupplierID, PicID) VALUES ('Chicken Oyster Thighs',10,1.51202,20,'kg',8,'chicken_thighs');
INSERT INTO Product (ProductName, CategoryID, SalePrice, Quantity, QuantityUnit, SupplierID, PicID) VALUES ('Chicken Drumsticks',10,3.44976,14,'kg',8,'chicken_drumsticks');
INSERT INTO Product (ProductName, CategoryID, SalePrice, Quantity, QuantityUnit, SupplierID, PicID) VALUES ('Lamb Cutlets',10,2.4128,3,'kg',8,'lamb_cutlets');
INSERT INTO Product (ProductName, CategoryID, SalePrice, Quantity, QuantityUnit, SupplierID, PicID) VALUES ('Lamb Leg',10,1.83529,10,'kg',1,'lamb_leg');
INSERT INTO Product (ProductName, CategoryID, SalePrice, Quantity, QuantityUnit, SupplierID, PicID) VALUES ('Pork Loin Chops',10,4.96203,24,'kg',8,'pork_chops');
INSERT INTO Product (ProductName, CategoryID, SalePrice, Quantity, QuantityUnit, SupplierID, PicID) VALUES ('Sea Bass Fillets',13,10.32,12,'kg',7,'sea_bass_fillets');
INSERT INTO Product (ProductName, CategoryID, SalePrice, Quantity, QuantityUnit, SupplierID, PicID) VALUES ('Hake Fillets',13,2.57478,17,'kg',7,'hake_fillets');
