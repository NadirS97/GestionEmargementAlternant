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
>- Contient dans le body de la requête un paramétre idEmargement qui permettra à un étudiant de valider sa présence pour cet emargement

- 200 : l'émargement est validé
- 409 : l'étudiant a déjà validé sont émargement c.a.d que sont Etat==Present 
- 404 : l'identifiant idEmargement ne correspond à aucun émargement existant
- 403 : si la personne authentifiée n'a pas accès à cette URI

---

**Cloture d'un emargement par un enseignant:**

PUT
www.localhost:8080/emargement/{idEmargement}
OU
www.localhost:8080/emargement/enseignant/{idEmargement}

>- Requête authentifiée uniquement disponible pour le rôle (Enseignant)
>- Contient dans le body de la requête un paramétre idEmargement qui permettra à un enseignant de clôturer l'émargement
   
- 200 : l'émargement est clos
- 409 : l'enseignant a déjà clôturé l'émargement
- 404 : l'identifiant idEmargement ne correspond à aucun émargement existant
- 403 : si la personne authentifiée n'a pas accès à cette URI

---

**Liste de tous les emargements clos par un personnel administratif:**

GET
www.localhost:8080/emargement/admin/clos

>- Requête authentifiée uniquement disponible pour le rôle (PersonnelAdm)
>- Retourne dans le body la liste des objets Emargement ayant comme etatEmargement==Clos

- 200 : si la liste a bien été récupérée `non vide`
- `404 : si aucun emargement n'a pour etatEmargement==Clos`
- 403 : si la personne authentifiée n'a pas accès à cette URI
---

**Liste de tous les emargements ouverts par un personnel administratif:**

GET
www.localhost:8080/emargement/admin/ouverts

>- Requête authentifiée uniquement disponible pour le rôle (PersonnelAdm)
>- Retourne dans le body la liste des objets Emargement ayant comme etatEmargement==Ouvert

- 200 : si la liste a bien été récupérée `non vide`
- `404 : si aucun emargement n'a pour etatEmargement==Ouvert`
- 403 : si la personne authentifiée n'a pas accès à cette URI

---

**Consultation de l'emargement par un personnel administratif:**

GET
www.localhost:8080/emargement/admin/{idEmargement}

>- Requête authentifiée uniquement disponible pour le rôle (PersonnelAdm)
>- Retourne dans le body l'objet Emargement correspondant à l'identifiant idEmargement

- 200 : si l'Emargement existe et est bien recupéré
- 404 : si aucun Emargement ne correspond à cet identifiant
- 403 : si la personne authentifiée n'a pas accès à cette URI

---

**Creation d'un nouvel emargement par un personnel administratif:**

POST
www.localhost:8080/emargement/admin/creation

>- Requête authentifiée uniquement disponible pour le rôle (PersonnelAdm)
>- Requiert dans le body de la requete une structure JSON de la classe Emargement 
   > contenant au moins les champs **A DEFINIR UNE FOIS QU'ON A TRAITE LA FACADE**

- 201 : si l'Emargement a pu être créé sans erreur `+ Location de la ressource créée`
- 406 : si les attributs de l'objet envoyés ne sont pas conformes aux attentes
- 403 : si la personne authentifiée n'a pas accès à cette URI

---

**Liste de tous les etudiants presents par un personnel administratif:**

GET
www.localhost:8080/emargement/admin/{idEmargement}/present

>- Requête authentifiée uniquement disponible pour le rôle (PersonnelAdm)
>- Retourne dans le body uniquement la liste des étudiants presents de l'objet Emargement correspondant à l'identifiant idEmargement

- 201 : si l'Emargement existe et la liste des étudiants presents est bien récupérée
- 404 : si aucun Emargement ne correspond à cet identifiant
- 403 : si la personne authentifiée n'a pas accès à cette URI

---

**Liste de tous les etudiants absents par un personnel administratif:**

GET
www.localhost:8080/emargement/admin/{idEmargement}/absent

>- Requête authentifiée uniquement disponible pour le rôle (PersonnelAdm)
>- Retourne dans le body uniquement la liste des étudiants absents de l'objet Emargement correspondant à l'identifiant idEmargement

- 201 : si l'Emargement existe et la liste des étudiants absents est bien récupérée
- 404 : si aucun Emargement ne correspond à cet identifiant
- 403 : si la personne authentifiée n'a pas accès à cette URI


---




| Fonctionalités                                                       | URI                                        | Méthode | Paramètres de la requête |
|:---------------------------------------------------------------------|--------------------------------------------|---------|--------------------------|
| Connexion                                                            | /emargement                                | POST    |                          |
| Emargement d'un etudiant                                             | /emargement/{idEmargement}/                | PUT     |                          |
| Cloture d'un emargement par un enseignant                            | /emargement/{idEmargement}/                | PUT     |                          |
| Liste de tous les emargements clos par un personnel administratif    | /emargement/{idAdm}/clos                   | GET     |                          |
| Liste de tous les emargements ouverts par un personnel administratif | /emargement/{idAdm}/ouverts                | GET     |                          |
| Consultation de l'emargement par un personnel administratif          | /emargement/{idAdm}/{idEmargement}         | GET     |                          |
| Creation d'un nouvel emargement par un personnel administratif       | /emargement/{idAdm}/creation               | POST    |                          |
| Liste de tous les etudiants presents par un personnel administratif  | /emargement/{idAdm}/{idEmargement}/present | GET     |                          |
| Liste de tous les etudiants absents par un personnel administratif   | /emargement/{idAdm}/{idEmargement}/absent  | GET     |                          |


**Proposition Nadir:**

| Fonctionalités                                                       | URI                                                                               | Méthode | Paramètres de la requête |
|:---------------------------------------------------------------------|-----------------------------------------------------------------------------------|---------|--------------------------|
| Connexion                                                            | /emargement                                                                       | POST    |                          |
| Emargement d'un etudiant                                             | /emargement/{idEmargement}                                                        | PUT     |                          |
| Cloture d'un emargement par un enseignant                            | 1- /emargement/{idEmargement}<br/>ou<br/>2- /emargement/enseignant/{idEmargement} | PUT     |                          |
| Liste de tous les emargements clos par un personnel administratif    | /emargement/admin/clos                                                            | GET     |                          |
| Liste de tous les emargements ouverts par un personnel administratif | /emargement/admin/ouverts                                                         | GET     |                          |
| Consultation de l'emargement par un personnel administratif          | /emargement/admin/{idEmargement}                                                  | GET     |                          |
| Creation d'un nouvel emargement par un personnel administratif       | /emargement/admin/creation                                                        | POST    |                          |
| Liste de tous les etudiants presents par un personnel administratif  | /emargement/admin/{idEmargement}/present                                          | GET     |                          |
| Liste de tous les etudiants absents par un personnel administratif   | /emargement/admin/{idEmargement}/absent                                           | GET     |                          |