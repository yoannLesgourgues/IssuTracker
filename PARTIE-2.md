# TP IssueTracker : découverte de Spring et des API REST

## Partie 2 : développement

1. Créez un package issue
2. En vous aidant du package user et du [diagramme de classe suivant][packages-user+issue-uml], ainsi que du 
[cours][cours-api-spring], codez les classes permettant de créer la fonctionnalité Issue 
*(on peut parler de feature)*

## => Étapes à suivre

Des tests ont été codés pour les classes IssueService et IssueController. 
Vous pouvez exécuter chaque classe de test indépendamment, en cliquant-droit sur la classe puis en lançant les tests.
### Première étape !
1. Ouvrez la classe IssueServiceTest
2. Cliquez sur un des types Issue en rouge. 
2. Apuyez sur Alt+Entrée, et sélectionnez l'option Create Class. Choisissez ensuite de la place dans le package issue 
**du package main.java !!!** *(c'est vraiment important)*
3. Implémentez ensuite cette classe Issue

### Deuxième étape 
1. Ouvrez de nouveau la classe IssueServiceTest
2. Répétez les étapes de la première étape pour créer l'interface IssueService et la classe IssueLocalService
3. Commentez toutes les méthodes de IssueServiceTest **sauf la première !**
4. Exécutez la classe IssueServiceTest. Vous devriez avoir un (et un seul !) test qui échoue.
5. Développez l'interface IssueService et la classe IssueLocalService de façon à passer ce premier test
6. Décommentez le deuxième test de la classe IssueServiceTest et recommencez à l'étape 4. Continuez jusqu'à passer tous
les tests.

### Troisième étape
Répétez les instructions de l'étape 2 avec la classe IssueControllerTest, jusqu'à avoir passé tous les tests

## Partie 3 :
Pour passer à la partie 3, allez dans l'onglet "Git" d'IntelliJ puis ouvrez l'option "Branches".
Cliquez ensuite sur la branche "feature-commentaire" puis dans les options proposées cliquez sur "Merge 
'feature-commentaire' into' main' "
Ouvrez ensuite le fichier PARTIE-3.md




[packages-user+issue-uml]: https://drive.google.com/file/d/1lnmB584XUOgQxtVJaJlfG9yLWnPNkyo-/view?usp=sharing
[cours-api-spring]: https://nathanael-gimenez.canoprof.fr/eleve/DA4%20-%20Programmation%20Avanc%C3%A9e/activities/API_REST_avec_Spring.html