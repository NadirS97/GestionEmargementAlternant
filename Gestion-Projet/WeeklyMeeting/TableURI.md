**Connexion:**

Role : (Etudiant, Enseignant, PersonnelAdmin) 

POST
www.localhost:8080/emargement

> - Requête ne nécessitant aucune authentification
> - Contient dans le body deux paramètres le login et le mot de passe

- 406 : quand les informations dans les paramètres sont incorrectes

---

**Emargement d'un etudiant:**

PUT
www.localhost:8080/emargement/{idEmargement}

>- Requête authentifiée uniquement disponible pour le rôle (Etudiant)
>- Contient dans le body de la requête un paramètre idEmargement qui permettra à un étudiant de valider sa présence pour cet emargement

- 200 : l'émargement est validé
- 409 : l'étudiant a déjà validé sont émargement c.a.d que son Etat==Present 
- 404 : l'identifiant idEmargement ne correspond à aucun émargement existant
- 403 : si la personne authentifiée n'a pas accès à cette URI

---

**Cloture d'un emargement par un enseignant:**

PUT
www.localhost:8080/emargement/{idEmargement}/cloture

>- Requête authentifiée uniquement disponible pour le rôle (Enseignant)
>- Contient dans le body de la requête un paramétre idEmargement qui permettra à un enseignant de clôturer l'émargement
   
- 200 : l'émargement est clos
- 409 : l'enseignant a déjà clôturé l'émargement
- 404 : l'identifiant idEmargement ne correspond à aucun émargement existant
- 403 : si la personne authentifiée n'a pas accès à cette URI

---

**Liste de tous les emargements clos par un personnel administratif:**

GET
www.localhost:8080/emargement/clos

>- Requête authentifiée uniquement disponible pour le rôle (PersonnelAdm)
>- Retourne dans le body la liste des objets Emargement ayant comme etatEmargement==Clos

- 200 : si la liste a bien été récupérée ` vide ou non vide`
- 403 : si la personne authentifiée n'a pas accès à cette URI
---

**Liste de tous les emargements ouverts par un personnel administratif:**

GET
www.localhost:8080/emargement/ouverts

>- Requête authentifiée uniquement disponible pour le rôle (PersonnelAdm)
>- Retourne dans le body la liste des objets Emargement ayant comme etatEmargement==Ouvert

- 200 : si la liste a bien été récupérée `vide ou non vide`
- 403 : si la personne authentifiée n'a pas accès à cette URI

---

**Consultation de l'emargement par un personnel administratif:**

GET
www.localhost:8080/emargement/{idEmargement}

>- Requête authentifiée uniquement disponible pour le rôle (PersonnelAdm)
>- Retourne dans le body l'objet Emargement correspondant à l'identifiant idEmargement

- 200 : si l'Emargement existe et est bien recupéré
- 404 : si aucun Emargement ne correspond à cet identifiant
- 403 : si la personne authentifiée n'a pas accès à cette URI

---

**Creation d'un nouvel emargement par un personnel administratif:**

POST
www.localhost:8080/emargement/creation

>- Requête authentifiée uniquement disponible pour le rôle (PersonnelAdm)
>- Requiert dans le body de la requete une structure JSON de la classe Emargement 
   > contenant au moins les champs **A DEFINIR UNE FOIS QU'ON A TRAITE LA FACADE**

- 201 : si l'Emargement a pu être créé sans erreur + Location de la ressource créée
- 406 : si les attributs de l'objet envoyés ne sont pas conformes aux attentes
- 403 : si la personne authentifiée n'a pas accès à cette URI

---

**Liste de tous les etudiants presents par un personnel administratif:**

GET
www.localhost:8080/emargement/{idEmargement}/present

>- Requête authentifiée uniquement disponible pour le rôle (PersonnelAdm)
>- Retourne dans le body uniquement la liste des étudiants presents de l'objet Emargement correspondant à l'identifiant idEmargement

- 201 : si l'Emargement existe et la liste des étudiants presents est bien récupérée
- 404 : si aucun Emargement ne correspond à cet identifiant
- 403 : si la personne authentifiée n'a pas accès à cette URI

---

**Liste de tous les etudiants absents par un personnel administratif:**

GET
www.localhost:8080/emargement/{idEmargement}/absent

>- Requête authentifiée uniquement disponible pour le rôle (PersonnelAdm)
>- Retourne dans le body uniquement la liste des étudiants absents de l'objet Emargement correspondant à l'identifiant idEmargement

- 201 : si l'Emargement existe et la liste des étudiants absents est bien récupérée
- 404 : si aucun Emargement ne correspond à cet identifiant
- 403 : si la personne authentifiée n'a pas accès à cette URI

---
**Fonctionnalités obligatoires:**

| Avancement                     | Fonctionnalités                                                      | URI                                | Méthode | Rôle         |
|--------------------------------|:---------------------------------------------------------------------|------------------------------------|---------|--------------|
|                                | Connexion                                                            | /emargement                        | POST    | All          |
| Fait (demande de verification) | Emargement d'un etudiant                                             | /emargement/{idEmargement}         | PUT     | Etudiant     |
| Fait (demande de verification) | Cloture d'un emargement par un enseignant                            | /emargement/{idEmargement}/cloture | PUT     | Enseignant   |
| Fait (demande de verification) | Liste de tous les emargements clos par un personnel administratif    | /emargement/clos                   | GET     | PersonnelAdm |
| Fait (demande de verification) | Liste de tous les emargements ouverts par un personnel administratif | /emargement/ouverts                | GET     | PersonnelAdm |
| Fait (demande de verification) | Consultation de l'emargement par un personnel administratif          | /emargement/{idEmargement}         | GET     | PersonnelAdm |
| Fait (demande de verification) | Creation d'un nouvel emargement par un personnel administratif       | /emargement/                       | POST    | PersonnelAdm |
| Fait (demande de verification) | Liste de tous les etudiants presents par un personnel administratif  | /emargement/{idEmargement}/present | GET     | PersonnelAdm |
| Fait (demande de verification) | Liste de tous les etudiants absents par un personnel administratif   | /emargement/{idEmargement}/absent  | GET     | PersonnelAdm |           

---

**Fonctionnalités optionnelles:**
<br>_Bonus gestion des entités (CRUD)_
<br>**Role:** PersonnelAdmin

| Fonctionnalités                                | URI                     | Méthode | Rôle         |
|:-----------------------------------------------|-------------------------|---------|--------------|
| Liste de tous les enseignants                  | /enseignant             | GET     | PersonnelAdm |
| Ajout d'un enseignant                          | /enseignant             | POST    | PersonnelAdm |
| Consultation des informations d'un enseignant  | /enseignant/{idEns}     | GET     | PersonnelAdm |
| Suppression d'un enseignant                    | /enseignant/{idEns}     | DELETE  | PersonnelAdm |
| Liste de tous les étudiants                    | /etudiant/              | GET     | PersonnelAdm |
| Ajout d'un étudiant                            | /etudiant/              | POST    | PersonnelAdm |
| Consultation des informations d'un étudiant    | /etudiant/{NumEtu}      | GET     | PersonnelAdm |
| Suppression d'un étudiant                      | /etudiant/{NumEtu}      | DELETE  | PersonnelAdm |
| Liste de tous les modules                      | /module/                | GET     | PersonnelAdm |
| Ajout d'un module                              | /module/                | POST    | PersonnelAdm |
| Consultation des informations d'un module      | /module/{CodeMod}       | GET     | PersonnelAdm |
| Suppression d'un module                        | /module/{CodeMod}       | DELETE  | PersonnelAdm |
| Modification des informations d'un module      | /module/{CodeMod}       | PUT     | PersonnelAdm |
| Liste de tous les semestres                    | /semestre/              | GET     | PersonnelAdm |
| Ajout d'un semestre                            | /semestre/              | POST    | PersonnelAdm |
| Consultation des informations d'un semestre    | /semestre/{nomSemestre} | GET     | PersonnelAdm |
| Suppression d'un semestre                      | /semestre/{nomSemestre} | DELETE  | PersonnelAdm |
| Modification des informations d'un semestre    | /semestre/{nomSemestre} | PUT     | PersonnelAdm |
| Ajout d'un sous-Module                         | /SMod/                  | POST    | PersonnelAdm |
| Liste de de tous les sous-Modules              | /SMod/                  | GET     | PersonnelAdm |
| Consultation des informations d'un sous-Module | /SMod/{NomSMod}         | GET     | PersonnelAdm |
| Ajout d'un groupe                              | /groupe/                | POST    | PersonnelAdm |
| Liste de de tous les groupes                   | /groupe/                | GET     | PersonnelAdm |
| Consultation des informations d'un groupe      | /groupe/{NomG}          | GET     | PersonnelAdm |
| Ajout d'une salle                              | /salle/                 | POST    | PersonnelAdm |
| Liste de de toutes les salles                  | /salle/                 | GET     | PersonnelAdm |
| Consultation des informations d'une salle      | /salle/{salle}          | GET     | PersonnelAdm |





