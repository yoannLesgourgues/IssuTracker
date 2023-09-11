# TP IssueTracker : découverte de Spring et des API REST

## Contexte
IssueTracker est un projet interne à votre ESN pour facilement suivre les problèmes rencontrés par les utilisateurs.

Il existe deux types d'utilisateurs :
-  les utilisateurs classiques, généralement des clients, qui ont la fonction USER
-  les développeurs, qui ont la fonctions DEVELOPER

Un utilisateur ou un développeur peut faire remonter un problème (Issue) rencontré sur l'application. Cette Issue est 
identifiée par un id unique, possède un titre et un contenu, ainsi qu'une date de création et une date de clôture.

Les utilisateurs et les développeurs peuvent discuter d'une Issue en postant des commentaires. Ces commentaires sont 
identifiés de manière unique par un id, possèdent un auteur, sont relatifs à une Issue, et possèdent un contenu.

Le modèle métier est consultable [ici][metier-uml]

## Partie 1 : découverte

1. Vérifiez que vous êtes bien sur la branche "main" du repository

Observez le [diagramme de classe du package user][package-user-uml]

### 1. Classe Controller
1. Ouvrez la classe UserController
2. À partir de vos connaissances et du [cours sur les API REST et Spring][cours-api-spring], définissez ce qu'est un 
contrôleur dans un modèle MV* (ex. : modèle MVC)
3. Identifiez la partie du code qui indique à Spring que cette classe est un controller REST
4. Déduisez ou cherchez le but de l'annotation @RequestMapping

#### A. GetMapping
1. 1. Lancez l'application (allez dans IssueTrackerStudentsApplication puis cliquez sur la flèche verte à côté du nom 
   de la classe)
   2. Retournez dans la classe UserController
   3. Dans l'annotation @GetMapping de la méthode getAll, cliquez sur le petit symbole web (le globe avec une toile)
   4. Sélectionnez l'option "Generate Request in HTTP Client"
   5. Dans le nouveau fichier ouvert, analysez le code généré puis exécutez-le (flèche verte à côté du code)
   6. Analysez les résultats
2. Déduisez-en le rôle de la méthode getAll ? Identifiez son type de retour ?
3. Déduisez-en le rôle de l'annotation @GetMapping de la méthode getAll ?
4. Répétez ces étapes avec la fonction getById. 
5. Quelle est la différence entre l'annotation de la méthode getAll et de la méthode getById ? Pourquoi ?
6. À partir de ces résultats, identifiez le rôle de l'annotation @PathVariable ?

#### B. PostMapping
1. Observez la méthode createUser
2. Générez une requête HTTP pour cette requête, copiez les valeurs suivantes :
```
POST http://localhost:8080/users
Content-Type: application/json

{
  "id": 9,
  "nom": "Machin",
  "fonction": "USER"
}
```
3. Exécutez la requête
4. Déduisez-en le rôle de l'annotation @PostMapping
5. Déduisez de ces résultats le rôle de l'annotation @RequestBody
6. À quoi correspond le code HTTP 204 ?
7. Déduisez ou cherchez le rôle de la classe ResponseEntity

#### C. Gestion des erreurs
1. Répétez les étapes de la partie B pour essayer de créer un User avec un id déjà existant. Que se passe-t-il ? Quel 
est le code de statut HTTP renvoyé ? 
2. Ouvrez la fonction create de la classe UserLocalService et analysez son code (et les fonctions qu'elle appelle). 
Qu'est-ce qui est renvoyé en cas de tentative d'insertion d'un utilisateur déjà existant ?
3. Ouvrez la classe ExceptionHandlingAdvice du package exceptions. Identifiez le mécanisme qui permet à Spring 
d'envoyer une erreur 409 - Conflict en cas de doublon.
4. Répétez ces étapes pour la fonction update de UserLocalService, et identifiez ce qui se passe en cas de tentative 
de modification d'un User inexistant (ou de suppression, pour la méthode delete)

#### D. <Method>Mapping
Finissez d'analyser UserController en trouvant le but des annotations @PutMapping et @DeleteMapping

### 2. Classe Service

#### A. Classe LocalService
Lisez la documentation de la classe LocalService et résumez son but et ses fonctionnalités

### B. Classe UserLocalService
1. De quelle(s) classe(s) hérite la classe UserLocalService ? Quelles interfaces implémente-t-elle ?
2. Quel est le but de la classe UserLocalService ?
3. À partir de vos connaissances et du [cours sur les API REST et Spring][cours-api-spring], définissez le rôle d'une 
classe Service dans un modèle MV*

#### C. Interface UserService
1. La classe UserController contient-elle un objet de type UserLocalService ou de type UserService ?
À l'exécution, quel est le type de l'objet qui lui sera concrètement fourni ? 
2. Pourquoi avoir séparé l'interface UserService et l'implémentation UserLocalService ? Quel principe de base de 
l'architecture (identifié lors du cours POO-Rappels-Java) est respecté par cette séparation ?

### 3. Survol rapide des tests unitaires
1. Ouvrez le package main.tests.user
2. Listez les classes qu'il contient
3. Faites un clic-droit sur le package, puis cliquez sur l'option "Run Tests in geiffel.da4...". Que se passe-t-il ?
4. Lisez rapidement un ou deux des tests de la classe UserServiceTest. Quel est l'intérêt de ce genre de tests selon 
vous ?

## Partie 2 : 
Pour passer à la partie 2, allez dans l'onglet "Git" d'IntelliJ puis ouvrez l'option "Branches".
Cliquez ensuite sur la branche "feature-issue" puis dans les options proposées cliquez sur "Merge 'feature-issue' into 
'main' "
Ouvrez ensuite le fichier PARTIE-2.md


[metier-uml]: https://drive.google.com/file/d/1hywxcUTtbVRyL7lheg-Xm3hR1cSKWP0F/view?usp=sharing
[package-user-uml]: https://drive.google.com/file/d/1fv3A-LL1bbCEDkctDX-feOvsbhBxbpwX/view?usp=sharing
[cours-api-spring]: https://nathanael-gimenez.canoprof.fr/eleve/DA4%20-%20Programmation%20Avanc%C3%A9e/activities/API_REST_avec_Spring.html