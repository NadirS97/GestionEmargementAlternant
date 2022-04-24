**Connexion:**

POST
www.localhost:8080/emargement

**Emargement d'un etudiant:**

PUT
www.localhost:8080/emargement/{idEmargement}/{numEtu}

**Cloture d'un emargement par un enseignant:**

PUT
www.localhost:8080/emargement/{idEmargement}/{idEnseignant}

**Liste de tous les emargements clos par un personnel administratif:**

GET
www.localhost:8080/emargement/{idAdm}/clos

**Liste de tous les emargements ouverts par un personnel administratif:**

GET
www.localhost:8080/emargement/{idAdm}/ouverts

**Consultation de l'emargement par un personnel administratif:**

GET
www.localhost:8080/emargement/{idAdm}/{idEmargement}

**Creation d'un nouvel emargement par un personnel administratif:**

POST
www.localhost:8080/emargement/{idAdm}/creation

**Liste de tous les etudiants presents par un personnel administratif:**

GET
www.localhost:8080/emargement/{idAdm}/{idEmargement}/present

**Liste de tous les etudiants absents par un personnel administratif:**

GET
www.localhost:8080/emargement/{idAdm}/{idEmargement}/absent




| Fonctionalités                                                       | URI                                        | Méthode | Paramètres de la requête |
|:---------------------------------------------------------------------|--------------------------------------------|---------|--------------------------|
| Connexion                                                            | /emargement                                | POST    |                          |
| Emargement d'un etudiant                                             | /emargement/{idEmargement}/                | PUT     |                          |
| Cloture d'un emargement par un enseignant                            | /emargement/{idEmargement}/  | PUT     |                          |
| Liste de tous les emargements clos par un personnel administratif    | /emargement/{idAdm}/clos                   | GET     |                          |
| Liste de tous les emargements ouverts par un personnel administratif | /emargement/{idAdm}/ouverts                | GET     |                          |
| Consultation de l'emargement par un personnel administratif          | /emargement/{idAdm}/{idEmargement}         | GET     |                          |
| Creation d'un nouvel emargement par un personnel administratif       | /emargement/{idAdm}/creation               | POST     |                          |
| Liste de tous les etudiants presents par un personnel administratif  | /emargement/{idAdm}/{idEmargement}/present | GET     |                          |
| Liste de tous les etudiants absents par un personnel administratif   | /emargement/{idAdm}/{idEmargement}/absent  | GET     |                          |