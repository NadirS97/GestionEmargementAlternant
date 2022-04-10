# Projet M1-S2 Emargement:

---
##Sujet : gestion des émargements

L’objectif de ce projet est d’informatiser et sécuriser la gestion des émargements. Le projet se concentre sur la création, l’alimentation et la consultation des émargements.

##Exigences:
Pour le service back-end (tous les groupes de projet)
- La forme, le nommage et la manière d'utiliser vos points d'entrée doivent être cohérents et pertinents, au regard des besoins fonctionnels exprimés.

Pour la réalisation du projet, vous devez utiliser les technologies suivantes :
- Service Web : SpringBoot REST API 
- Documentation : Spring REST Docs (avec génération de snippets de tests)
- Base de données : MongoDB 
- Déploiement : Docker

##Tâches:
**Trois acteurs:**
- Un étudiant:
    > Emarge pour signifier sa présence lors d'une séance de cours
- Un enseignant:
    > Valide les emargements
- Un membre du personnel administratif:
    > Crée liste d'émargements
<br/> Consultation la liste d'émargements 

- Liste de tous les étudiants par groupe
- Liste des étudiants présents
- Liste des étudiants absents
- Un semestre est composé d'un ou plusieurs modules
- Un module est composé de plusieurs types de cours (CM/TD/TP)
- Un type de cours (TD/TP) est composé de plusieurs groupes
- Un étudiant est inscrit dans un groupe selon le type de cours
- Chaque étudiant possède un état "Absent-Present"
- Un enseignant est rattaché à un ou plusieurs groupes
- Un émargement est composé d'une heure, et d'une date et d'un groupe et d'étudiants
- Scan de QR code fait appel à la fonction de changement d'état (absent/présent) selon le groupe
- Un enseignant scan le QR Code (à la find e l'heure) qui va clôturer l'URL (les émargements)
- Un membre administratif génère la liste d'émargements
- Un membre administratif peut consulter l'historique de toutes les listes d'émargements (clôses et ouvertes et en attente)
- L'URL (une fois qu'on scan de QR code) nous envoie vers une page d'authentificaiton:
  - Si utilisateur = prof alors avoir la possibilité de clôturer le QR Code
  - Si utilisateur = étudiant alors avoir la possibilité de valider sa présence
- Un membre administratif peut
  - consulter la liste des identifiants (QR code) qui sont en cours de circulation 
  - consulter la liste des identifiants (QR code) qui sont clôturés
  - creer/generer un nouvel identifiant (QR code)
  - consulter les listes d'émargements en accédant à l'identifiant d'un QR code clôturé
- Un QR code possède un temps de validité afin de s'auto-clôturer dans le cas ou le prof n'a pas clôturer (entre autre pour "le travail en autonomie")

###Optionnel:
- Recherche de l'historique des présences d'un seul étudiant

##Etapes à suivre:
1. Création des entités (Etudiant, Utilisateur, Enseignant, PersonnelAdministratif, Semestre, Module, Groupe, Emargement)

2. Création de la base de données (Emargements)

3. Création de la table des URI et des contrôleurs (Mapping GET & POST)

4. Génération des API

5. Génération config (Spring security)

6. Requêtes CRUD

7. Création / Génération des tests

8. Documentation: Spring REST Docs (Au fur et à mesure)

