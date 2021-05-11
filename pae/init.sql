-- Schema: pae

DROP SCHEMA IF EXISTS pae CASCADE;
CREATE SCHEMA pae;


--# CREATION DES TABLES

CREATE TABLE pae.inscrits(
	id_inscrit SERIAL PRIMARY KEY,
	date_inscription DATE NOT NULL DEFAULT now(),
	nom VARCHAR(50) NOT NULL,
	prenom VARCHAR(50) NOT NULL,
	pseudo VARCHAR(50) NOT NULL,
	mdp VARCHAR(250) NOT NULL,
	mail VARCHAR(100) NOT NULL,
	fonction VARCHAR(2) NOT NULL
);

INSERT INTO pae.inscrits values(DEFAULT, DEFAULT, 'Ach', 'Achille', 'achil', 'mdpuser', 'ach.ile@student.vinci.be', 'E');
INSERT INTO pae.inscrits values(DEFAULT, DEFAULT, 'Bas', 'Basile', 'bazz', 'mdpuser', 'bas.ile@student.vinci.be', 'E');
INSERT INTO pae.inscrits values(DEFAULT, DEFAULT, 'Car', 'Caroline', 'caro', 'mdpuser', 'car.line@student.vinci.be', 'E');

INSERT INTO pae.inscrits values(DEFAULT, DEFAULT, 'Lehmann', 'Brigitte', 'bri', 'Admin;10.', 'brigitte.lehmann@vinci.be', 'PR');

INSERT INTO pae.inscrits values(DEFAULT, DEFAULT, 'Leuleux', 'Laurent', 'lau', 'Rouge;7?', 'Laurent.leleux@vinci.be', 'P');



CREATE TABLE pae.pays(
	id_pays SERIAL PRIMARY KEY,
	nom varchar(100) NOT NULL,
	code VARCHAR(3) NOT NULL,
	programme VARCHAR(50) NOT NULL
	

);

INSERT INTO pae.pays VALUES
    (DEFAULT, 'Afrique du Sud','ZA','FAME'),
    (DEFAULT,'Albanie','AL','FAME'),
    (DEFAULT,'Algerie','DZ','FAME'),
    (DEFAULT,'Allemagne','DE','DEU'),
    (DEFAULT,'Andorre','AD','Erasmus+'),
    (DEFAULT,'Angola','AO','FAME'),
    (DEFAULT,'Anguilla','AI','FAME'),
    (DEFAULT,'Antarctique','AQ','FAME'),
    (DEFAULT,'Antigua et Barbuda','AG','FAME'),
    (DEFAULT,'Antilles Néerlandaises','AN','FAME'),
    (DEFAULT,'Arabie Saoudite','SA','FAME'),
    (DEFAULT,'Argentine','AR','FAME'),
    (DEFAULT,'Arménie','AM','FAME'),
    (DEFAULT,'Aruba','AW','FAME'),
    (DEFAULT,'Australie','AU','FAME'),
    (DEFAULT,'Autriche','AT','FAME'),
    (DEFAULT,'Azerbaidjan','AZ','FAME'),
    (DEFAULT,'Bahamas','BS','FAME'),
    (DEFAULT,'Bahrein','BH','FAME'),
    (DEFAULT,'Bangladesh','BD','FAME'),
    (DEFAULT,'Barbade','BB','FAME'),
    (DEFAULT,'Bélarus (Biélorussie)','BY','Erasmus+'),
    (DEFAULT,'Belgique','BE','Erabel'),
    (DEFAULT,'Belize','BZ','FAME'),
    (DEFAULT,'Bénin','BJ','FAME'),
    (DEFAULT,'Bermudes','BM','FAME'),
    (DEFAULT,'Bhoutan','BT','FAME'),
    (DEFAULT,'Bolivie','BO','FAME'),
    (DEFAULT,'Bosnie et Herzégovine','BA','Erasmus+'),
    (DEFAULT,'Botswana','BW','FAME'),
    (DEFAULT,'Bouvet Island','BV','Erasmus+'),
    (DEFAULT,'Brésil','BR','FAME'),
    (DEFAULT,'Brunei','BN','FAME'),
    (DEFAULT,'Bulgarie','BG','FAME'),
    (DEFAULT,'Burkina Faso','BF','FAME'),
    (DEFAULT,'Burundi','BI','FAME'),
    (DEFAULT,'Cambodge','KH','FAME'),
    (DEFAULT,'Cameroun','CM','FAME'),
    (DEFAULT,'Canada','CA','FAME'),
    (DEFAULT,'Cap Vert','CV','FAME'),
    (DEFAULT,'Chili','CL','FAME'),
    (DEFAULT,'Chine','CN','FAME'),
    (DEFAULT,'Chypre','CY','Erasmus+'),
    (DEFAULT,'Cité du Vatican (Holy See)','VA','Erasmus+'),
    (DEFAULT,'Colombie','CO','FAME'),
    (DEFAULT,'Comores','KM','FAME'),
    (DEFAULT,'Congo, République','CG','FAME'),
    (DEFAULT,'Congo, République Démocratique du','CD','FAME'),
    (DEFAULT,'Corée du Nord','KP','FAME'),
    (DEFAULT,'Corée du Sud','KR','FAME'),
    (DEFAULT,'Costa Rica','CR','FAME'),
    (DEFAULT,'Côte d''Ivoire','CI','FAME'),
    (DEFAULT,'Croatie','HR','FAME'),
    (DEFAULT,'Cuba','CU','FAME'),
    (DEFAULT,'Curacao','CW','FAME'),
    (DEFAULT,'Danemark','DK','Erasmus+'),
    (DEFAULT,'Djibouti','DJ','FAME'),
    (DEFAULT,'Dominique','DM','FAME'),
    (DEFAULT,'Egypte','EG','FAME'),
    (DEFAULT,'Emirats Arabes Unis','AE','FAME'),
    (DEFAULT,'Equateur','EC','FAME'),
    (DEFAULT,'Erythrée','ER','FAME'),
    (DEFAULT,'Espagne','ES','Erasmus+'),
    (DEFAULT,'Estonie','EE','Erasmus+'),
    (DEFAULT,'Etats-Unis','US','FAME'),
    (DEFAULT,'Ethiopie','ET','FAME'),
    (DEFAULT,'Fidji','FJ','FAME'),
    (DEFAULT,'Finlande','FI','Erasmus+'),
    (DEFAULT,'France','FR','Erasmus+'),
    (DEFAULT,'France, Métropolitaine','FX','Erasmus+'),
    (DEFAULT,'Gabon','GA','FAME'),
    (DEFAULT,'Gambie','GM','FAME'),
    (DEFAULT,'Gaza','PS','FAME'),
    (DEFAULT,'Géorgie','GE','FAME'),
    (DEFAULT,'Géorgie du Sud et les îles Sandwich du Sud','GS','FAME'),
    (DEFAULT,'Ghana','GH','FAME'),
    (DEFAULT,'Gibraltar','GI','Erasmus+'),
    (DEFAULT,'Grèce','GR','Erasmus+'),
    (DEFAULT,'Grenade','GD','Erasmus+'),
    (DEFAULT,'Greoenland','GL','Erasmus+'),
    (DEFAULT,'Guadeloupe','GP','FAME'),
    (DEFAULT,'Guam','GU','FAME'),
    (DEFAULT,'Guatemala','GT','FAME'),
    (DEFAULT,'Guinée','GN','FAME'),
    (DEFAULT,'Guinée Bissau','GW','FAME'),
    (DEFAULT,'Guinée Equatoriale','GQ','FAME'),
    (DEFAULT,'Guyane','GY','FAME'),
    (DEFAULT,'Guyane Française','GF','FAME'),
    (DEFAULT,'Haïti','HT','FAME'),
    (DEFAULT,'Honduras','HN','FAME'),
    (DEFAULT,'Hong Kong','HK','FAME'),
    (DEFAULT,'Hongrie','HU','Erasmus+'),
    (DEFAULT,'Ile de Man','IM','Erasmus+'),
    (DEFAULT,'Iles Caïman','KY','FAME'),
    (DEFAULT,'Iles Christmas','CX','FAME'),
    (DEFAULT,'Iles Cocos','CC','FAME'),
    (DEFAULT,'Iles Cook','CK','FAME'),
    (DEFAULT,'Iles Féroé','FO','FAME'),
    (DEFAULT,'Iles Guernesey','GG','FAME'),
    (DEFAULT,'Iles Heardet McDonald','HM','FAME'),
    (DEFAULT,'Iles Malouines','FK','FAME'),
    (DEFAULT,'Iles Mariannes du Nord','MP','FAME'),
    (DEFAULT,'Iles Marshall','MH','FAME'),
    (DEFAULT,'Iles Maurice','MU','FAME'),
    (DEFAULT,'Iles mineures éloignées des Etats-Unis','UM','FAME'),
    (DEFAULT,'Iles Norfolk','NF','FAME'),
    (DEFAULT,'Iles Salomon','SB','FAME'),
    (DEFAULT,'Iles Turques et Caïque','TC','FAME'),
    (DEFAULT,'Iles Vierges des Etats-Unis','VI','FAME'),
    (DEFAULT,'Iles Vierges du Royaume-Uni','VG','FAME'),
    (DEFAULT,'Inde','IN','FAME'),
    (DEFAULT,'Indonésie','ID','FAME'),
    (DEFAULT,'Iran','IR','FAME'),
    (DEFAULT,'Iraq','IQ','FAME'),
    (DEFAULT,'Irlande','IE','Erasmus+'),
    (DEFAULT,'Islande','IS','Erasmus+'),
    (DEFAULT,'Israël','IL','FAME'),
    (DEFAULT,'Italie','IT','Erasmus+'),
    (DEFAULT,'Jamaique','JM','FAME'),
    (DEFAULT,'Japon','JP','FAME'),
    (DEFAULT,'Jersey','JE','FAME'),
    (DEFAULT,'Jordanie','JO','FAME'),
    (DEFAULT,'Kazakhstan','KZ','FAME'),
    (DEFAULT,'Kenya','KE','FAME'),
    (DEFAULT,'Kirghizistan','KG','FAME'),
    (DEFAULT,'Kiribati','KI','FAME'),
    (DEFAULT,'Kosovo','XK','FAME'),
    (DEFAULT,'Koweit','KW','FAME'),
    (DEFAULT,'Laos','LA','FAME'),
    (DEFAULT,'Lesotho','LS','FAME'),
    (DEFAULT,'Lettonie','LV','Erasmus+'),
    (DEFAULT,'Liban','LB','FAME'),
    (DEFAULT,'Libéria','LR','FAME'),
    (DEFAULT,'Libye','LY','FAME'),
    (DEFAULT,'Liechtenstein','LI','Erasmus+'),
    (DEFAULT,'Lituanie','LT','Erasmus+'),
    (DEFAULT,'Luxembourg','LU','Erasmus+'),
    (DEFAULT,'Macao','MO','FAME'),
    (DEFAULT,'Macédoine','MK','Erasmus+'),
    (DEFAULT,'Madagascar','MG','FAME'),
    (DEFAULT,'Malaisie','MY','FAME'),
    (DEFAULT,'Malawi','MW','FAME'),
    (DEFAULT,'Maldives','MV','FAME'),
    (DEFAULT,'Mali','ML','FAME'),
    (DEFAULT,'Malte','MT','Erasmus+'),
    (DEFAULT,'Maroc','MA','FAME'),
    (DEFAULT,'Martinique','MQ','FAME'),
    (DEFAULT,'Mauritanie','MR','FAME'),
    (DEFAULT,'Mayotte','YT','FAME'),
    (DEFAULT,'Mexique','MX','FAME'),
    (DEFAULT,'Micronésie','FM','FAME'),
    (DEFAULT,'Moldavie','MD','FAME'),
    (DEFAULT,'Monaco','MC','Erasmus+'),
    (DEFAULT,'Mongolie','MN','FAME'),
    (DEFAULT,'Monténégro','ME','FAME'),
    (DEFAULT,'Montserrat','MS','FAME'),
    (DEFAULT,'Mozambique','MZ','FAME'),
    (DEFAULT,'Myanmar (Birmanie)','MM','FAME'),
    (DEFAULT,'Namibie','NA','FAME'),
    (DEFAULT,'Nauru','NR','FAME'),
    (DEFAULT,'Népal','NP','FAME'),
    (DEFAULT,'Nicaragua','NI','FAME'),
    (DEFAULT,'Niger','NE','FAME'),
    (DEFAULT,'Nigeria','NG','FAME'),
    (DEFAULT,'Niue','NU','FAME'),
    (DEFAULT,'Norvège','NO','NOR'),
    (DEFAULT,'Nouvelle Calédonie','NC','FAME'),
    (DEFAULT,'Nouvelle Zélande','NZ','FAME'),
    (DEFAULT,'Oman','OM','FAME'),
    (DEFAULT,'Ouganda','UG','FAME'),
    (DEFAULT,'Ouzbékistan','UZ','FAME'),
    (DEFAULT,'Pakistan','PK','FAME'),
    (DEFAULT,'Palau','PW','FAME'),
    (DEFAULT,'Panama','PA','FAME'),
    (DEFAULT,'Papouasie Nouvelle Guinée','PG','FAME'),
    (DEFAULT,'Paraguay','PY','FAME'),
    (DEFAULT,'Pays-Bas','NL','Erasmus+'),
    (DEFAULT,'Pérou','PE','FAME'),
    (DEFAULT,'Philippines','PH','FAME'),
    (DEFAULT,'Pitcairn','PN','FAME'),
    (DEFAULT,'Pologne','PL','Erasmus+'),
    (DEFAULT,'Polynésie Française','PF','FAME'),
    (DEFAULT,'Porto Rico','PR','FAME'),
    (DEFAULT,'Portugal','PT','Erasmus+'),
    (DEFAULT,'Qatar','QA','FAME'),
    (DEFAULT,'République Centraficaine','CF','FAME'),
    (DEFAULT,'République Dominicaine','DO','FAME'),
    (DEFAULT,'République Tchèque','CZ','FAME'),
    (DEFAULT,'Réunion','RE','FAME'),
    (DEFAULT,'Roumanie','RO','Erasmus+'),
    (DEFAULT,'Royaume Uni','GB','Erasmus+'),
    (DEFAULT,'Russie','RU','FAME'),
    (DEFAULT,'Rwanda','RW','FAME'),
    (DEFAULT,'Sahara Occidental','EH','FAME'),
    (DEFAULT,'Saint Barthelemy','BL','FAME'),
    (DEFAULT,'Saint Hélène','SH','FAME'),
    (DEFAULT,'Saint Kitts et Nevis','KN','FAME'),
    (DEFAULT,'Saint Martin','MF','FAME'),
    (DEFAULT,'Saint Pierre et Miquelon','PM','FAME'),
    (DEFAULT,'Saint Vincent et les Grenadines','VC','FAME'),
    (DEFAULT,'Sainte Lucie','LC','FAME'),
    (DEFAULT,'Salvador','SV','FAME'),
    (DEFAULT,'Samoa Americaines','AS','FAME'),
    (DEFAULT,'Samoa Occidentales','WS','FAME'),
    (DEFAULT,'San Marin','SM','Erasmus+'),
    (DEFAULT,'Sao Tomé et Principe','ST','FAME'),
    (DEFAULT,'Sénégal','SN','FAME'),
    (DEFAULT,'Serbie','RS','FAME'),
    (DEFAULT,'Seychelles','SC','FAME'),
    (DEFAULT,'Sierra Léone','SL','FAME'),
    (DEFAULT,'Singapour','SG','FAME'),
    (DEFAULT,'Slovaquie','SK','FAME'),
    (DEFAULT,'Slovénie','SI','FAME'),
    (DEFAULT,'Somalie','SO','FAME'),
    (DEFAULT,'Soudan','SD','FAME'),
    (DEFAULT,'Sri Lanka','LK','FAME'),
    (DEFAULT,'Sud Soudan','SS','FAME'),
    (DEFAULT,'Suède','SE','Erasmus+'),
    (DEFAULT,'Suisse','CH','Erasmus+'),
    (DEFAULT,'Surinam','SR','FAME'),
    (DEFAULT,'Svalbard et Jan Mayen','SJ','FAME'),
    (DEFAULT,'Swaziland','SZ','FAME'),
    (DEFAULT,'Syrie','SY','FAME'),
    (DEFAULT,'Tadjikistan','TJ','FAME'),
    (DEFAULT,'Taiwan','TW','FAME'),
    (DEFAULT,'Tanzanie','TZ','FAME'),
    (DEFAULT,'Tchad','TD','FAME'),
    (DEFAULT,'Terres Australes et Antarctique Françaises','TF','FAME'),
    (DEFAULT,'Térritoire Britannique de l''Océan Indien','IO','FAME'),
    (DEFAULT,'Territoires Palestiniens occupés (Gaza)','PS','FAME'),
    (DEFAULT,'Thaïlande','TH','FAME'),
    (DEFAULT,'Timor-Leste','TL','FAME'),
    (DEFAULT,'Togo','TG','FAME'),
    (DEFAULT,'Tokelau','TK','FAME'),
    (DEFAULT,'Tonga','TO','FAME'),
    (DEFAULT,'Trinité et Tobago','TT','FAME'),
    (DEFAULT,'Tunisie','TN','FAME'),
    (DEFAULT,'Turkmenistan','TM','FAME'),
    (DEFAULT,'Turquie','TR','FAME'),
    (DEFAULT,'Tuvalu','TV','FAME'),
    (DEFAULT,'Ukraine','UA','Erasmus+'),
    (DEFAULT,'Uruguay','UY','FAME'),
    (DEFAULT,'Vanuatu','VU','FAME'),
    (DEFAULT,'Venezuela','VE','FAME'),
    (DEFAULT,'Vietnam','VN','FAME'),
    (DEFAULT,'Wallis et Futuna','WF','FAME'),
    (DEFAULT,'Yemen','YE','FAME'),
    (DEFAULT,'Zambie','ZM','FAME'),
    (DEFAULT,'Zimbabwe','ZW','FAME');

CREATE TABLE pae.etudiants(
	id_inscrit INTEGER REFERENCES pae.inscrits(id_inscrit) NOT NULL,
	nationalite INTEGER REFERENCES pae.pays(id_pays) NULL,
	date_naissance DATE NULL,
	adresse VARCHAR(255) NULL,
	tel VARCHAR(20) NULL,
	sexe_etudiant CHAR NULL,
	statut_etudiant VARCHAR(5) NULL,
	nbr_annee_etudes INTEGER NULL,
	num_compte_bancaire VARCHAR(255) NULL,
	titulaire_compte VARCHAR(100),
	nom_banque VARCHAR(100) NULL,
	code_bic VARCHAR(8) NULL,
	departement VARCHAR(100) NULL,
	numero INTEGER NULL,
	code_postal VARCHAR(10) NULL,
	email VARCHAR(255) NOT NULL,
	version INTEGER NOT NULL


);


INSERT INTO pae.etudiants values(1, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 'ach.ile@student.vinci.be', DEFAULT);
INSERT INTO pae.etudiants values(2, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 'bas.ile@student.vinci.be', DEFAULT);
INSERT INTO pae.etudiants values(3, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 'car.line@student.vinci.be', DEFAULT);

CREATE TABLE pae.partenaires(
	id_partenaire SERIAL PRIMARY KEY,
	nom_legal VARCHAR(255) NOT NULL,
	nom_affaire VARCHAR(255) NULL,
	nom_complet VARCHAR(255) NULL,
	departement VARCHAR(255),
	type_organisation VARCHAR(4),
	nbr_employe INTEGER,
	adresse VARCHAR(255) NULL,
	pays INTEGER REFERENCES pae.pays(id_pays) NULL,
	region VARCHAR(255),
	code_postal VARCHAR(10) NULL,
	ville VARCHAR(255) NULL,
	email VARCHAR(255) NULL,
	site_web VARCHAR(255),
	tel VARCHAR(50) NULL,
	code VARCHAR(3) NULL,
	version INTEGER NOT NULL
);


INSERT INTO pae.partenaires values (DEFAULT,'UC Leuven-Limburg (Leuven campus)', 'UC Leuven-Limburg (Leuven campus)', 'UC Leuven-Limburg (Leuven campus)',
			'Computer Sciences', null, null, 'Abdij Van Park 9', 23, null, '3001', 'Heverlee', 'international@ucll.be', 'https://www.ucll.be/', '+32(0)16 375 735', 'SMS',DEFAULT);

INSERT INTO pae.partenaires values (DEFAULT,'Technological University Dublin', 'Technological University Dublin', 'Technological University Dublin',
			'Computing', null, null, '40- 45 Mount Joy Square', 115, null, 'Dublin 1', 'Dublin', 'erasmus@dit.ie', 'www.dit.ie/computing/', '+35314023404', 'SMS',DEFAULT);

INSERT INTO pae.partenaires values (DEFAULT,'Universite de Luxembourg', 'Université de Luxembourg', 'Université de Luxembourg',
			'Computing', null, null, '7, avenue des Hauts-Fourneaux', 137, null, 'L-4362', 'Esch-Sur-Alzette', 'erasmsus@uni.lu', 'https://wwwwwwfr.uni.lu/', '(+352)46 66 44 4000', 'SMS',DEFAULT);


INSERT INTO pae.partenaires values (DEFAULT,'Wolfel Engineering GmbH', 'Wölfel Engineering GmbH', 'Wölfel Engineering GmbH',
			'Data processing and analytics', null, null, 'Max-Planck-Str.15', 4, null, '97204', 'Höchlberg', 'tr@woelfel.de', 'https://www.woelfel.de/home.html', '+49 (931) 49708-168', 'SMP',DEFAULT);


INSERT INTO pae.partenaires values (DEFAULT,'HES-SO Haute ecole spécialisée de Suisse occidentale', 'HES-SO Haute école spécialisée de Suisse occidentale (Haute école de gestion de Genève (HEG GE))', 'HES-SO Haute école spécialisée de Suisse occidentale (Haute école de gestion de Genève (HEG GE))',
			'Business Information Systems', null, null, '17, Rue de la Tambourine', 119, null, '1227', 'Carouge-Genève', 'international@hes-so.ch', null, '0041 22 388 17 00', 'SMS');


INSERT INTO pae.partenaires values (DEFAULT,'cegep Edouard Monpetit', 'cégep Edouard Monpetit', 'cégep Edouard Monpetit',
			'Techniques de l informatique', null, null, '3 800, rue Sherbrooke Est', 39, 'Québec', 'J4H 3M6', 'Longueuil', 'mobilites@cegepmonpetit.ca', 'http://www.cegepmontpetit.ca/', '450 679-2631', 'SMS');

INSERT INTO pae.partenaires values (DEFAULT,'College de Maisonneuve', 'Collège de Maisonneuve', 'Collège de Maisonneuve',
			'Techniques de l informatique', null, null, '945 chemin de Chambly', 39, 'Québec', 'H1X 2A2', 'Montréal', 'international@cmaisonneuve.qc.ca', 'https://www.cmaisonneuve.qc.ca/', '514 254-7131', 'SMS');

INSERT INTO pae.partenaires values (DEFAULT,'Cegep de Chicoutimi', 'Cégep de Chicoutimi', 'Cégep de Chicoutimi',
			'Techniques de l informatique', null, null, '534 Jacques-Cartier Est', 39, 'Québec', 'G7H 1Z6', 'Chicoutimi', 'mobilitesetudiantes@cchic.ca', 'https://cchic.ca', '418 549.9520  | Poste 1144', 'SMS');



CREATE TABLE pae.mobilites(
	id_mobilite SERIAL PRIMARY KEY,
	id_inscrit INTEGER REFERENCES pae.inscrits(id_inscrit) NOT NULL,
	annee_acad VARCHAR(255) NOT NULL,
	num_ordre_preference INTEGER,
	pays INTEGER REFERENCES pae.pays(id_pays) NULL,
	partenaire INTEGER REFERENCES pae.partenaires(id_partenaire) NULL,
	date_introduction DATE NOT NULL,
	quadri INTEGER NULL,
	mobilite_encodee BOOLEAN NOT NULL,
	programme VARCHAR(255) NOT NULL,
	version INTEGER NOT NULL,
	code VARCHAR(3) NULL
);



INSERT INTO pae.mobilites values(DEFAULT,3,2019,1,115, 2,'2019-03-11',1,'false','Erasmus+');
INSERT INTO pae.mobilites values(DEFAULT,3,2019,2,137, 3,'2019-03-11',1,'false','Erasmus+');
INSERT INTO pae.mobilites values(DEFAULT,3,2019,3,null, null,'2019-03-11',null,'false','Fame');
INSERT INTO pae.mobilites values(DEFAULT,1,2019,1,null, null,'2019-03-11',null,'false','Fame');




CREATE TABLE pae.raisons_annulation(
	id_raison SERIAL PRIMARY KEY,
	raison VARCHAR(255) NOT NULL,
	version INTEGER NOT NULL

);

INSERT INTO pae.raisons_annulation VALUES(DEFAULT, 'Annulation !');
INSERT INTO pae.raisons_annulation VALUES(DEFAULT, 'Annulation 2 !');

CREATE TABLE pae.statut_mobilite(
	id_statut SERIAL PRIMARY KEY,
	etat VARCHAR(3),
	mobilite INTEGER REFERENCES pae.mobilites(id_mobilite),
	envoi_etudiant BOOLEAN,
	envoi_demande_paiement_he BOOLEAN,
	annule BOOLEAN,
	raison_annulation_etudiant VARCHAR(255),
	raison_annulation_prof INTEGER REFERENCES pae.raisons_annulation(id_raison),
	documents_signes BOOLEAN,
	seconde_demande_paiement_he BOOLEAN,
	contrat_bourse BOOLEAN,
	convention_stage BOOLEAN,
	convention_etude BOOLEAN,
	charte_etudiant BOOLEAN,
	document_engagement BOOLEAN,
	demande_premier_paiement BOOLEAN,
	premier_paiement BOOLEAN,
	info_etudiant BOOLEAN,
	info_partenaire BOOLEAN,
	attestation_sejour BOOLEAN,
	releve_notes BOOLEAN,
	certificat_stage BOOLEAN,
	rapport_final BOOLEAN,
	demande_second_paiement BOOLEAN,
	second_paiement BOOLEAN,
	parti_en_mobilite BOOLEAN,
	version INTEGER NOT NULL

);

INSERT INTO pae.statut_mobilite VALUES(DEFAULT, 'E', 1, false, false, false, 'Annulation etud!', 1, false, false, false, false, false, false, false, false, false,
 false, false, false, false, false, false, false, false, false);
