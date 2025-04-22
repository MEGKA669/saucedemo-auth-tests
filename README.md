# saucedemo-auth-tests
Premier projet d’automatisation de tests en Java (Selenium + TestNG) pour apprendre les bases du test automatisé
# 🧪 Projet d'Automatisation de Tests - SauceDemo

Ce projet est une suite de **tests automatisés** réalisée sur le site [SauceDemo](https://www.saucedemo.com) dans le cadre de mon autoformation en test logiciel. Il couvre plusieurs scénarios fonctionnels liés à l'authentification et au processus d'achat.

##Fonctionnalités testées

- Connexion (authentification) avec différents types de comptes :  
  `standard_user`, `locked_out_user`, `invalid_user`, etc.
- Tri des produits (par prix : du plus cher au moins cher, ou par ordre alphabétique)
- Ajout de produit(s) au panier
- Passage à la caisse (checkout)
- Déconnexion

##Technologies utilisées

- **Java**
- **Selenium WebDriver** – pour l'automatisation des interactions avec le navigateur
- **TestNG** – framework de tests
- **DataProvider** – pour injecter plusieurs jeux de données dans les tests
- **ExtentReports** – pour générer des rapports HTML clairs
- **Allure Reports** – pour des rapports plus professionnels et détaillés
- **SLF4J + Logback** – pour la gestion des logs

##Structure du projet

- `pages/` – classes Page Object (ex: `loginPage.java`)
- `test/` – classes de tests (ex: `authentificationTest.java`)
- `utils/` – classes utilitaires comme `user.java` et `userDataProvider.java`
- `logback.xml` – configuration des logs
- `testng.xml` – configuration des tests à lancer

##Exécution des tests

Assurez-vous d'avoir Java et Maven installés, puis lancez :

```bash
mvn clean test
