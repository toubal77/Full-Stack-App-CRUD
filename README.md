# Projet Full Stack : Application Angular et Spring Boot avec Authentification Keycloak

## Description

Ce projet est une application web construite avec Angular pour le frontend et Spring Boot pour le backend. L'authentification est gérée via Keycloak. L'application tourne dans un environnement Docker.

## Fonctionnalités

- **Authentification** : Utilisation de Keycloak pour l'authentification SSO.
- **Frontend Angular** : Interface utilisateur moderne et réactive.
- **Backend Spring Boot** : Gestion de la logique métier et des API.
- **Swagger** : Documentation interactive des API.

## Prérequis

- [Docker](https://www.docker.com/)
- [Node.js](https://nodejs.org/)
- [Java JDK 21+](https://www.oracle.com/java/technologies/javase-jdk21-downloads.html)

## Lancement de l'application

1. Clonez ce dépôt sur votre machine :

   ```bash
   git clone git@github.com:toubal77/CRUD-Full-Stack-App.git
   cd CRUD-Full-Stack-App
   ```

2. **Démarrez les containers Docker** :

   ```bash
   docker-compose up --build
   ```

   Cela lancera les services suivants :

   - **Angular** : Frontend accessible via [http://localhost:4200](http://localhost:4200)
   - **Spring Boot** : Backend accessible via [http://localhost:8080](http://localhost:8080)
   - **Swagger** : Documentation des API accessible via [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
   - **Keycloak** : Service d'authentification, accessible via [http://localhost:8081](http://localhost:8081)

3. **Configuration Keycloak** :

   - Accédez à Keycloak à [http://localhost:8081](http://localhost:8081) avec les identifiants suivants :
     - **Nom d'utilisateur** : `admin`
     - **Mot de passe** : `admin`

4. **Accès à l'application** :
   - Vous pouvez vous connecter en tant qu'administrateur de l'application avec les identifiants :
     - **Nom d'utilisateur** : `admin`
     - **Mot de passe** : `admin`
   - Si vous n'êtes pas connecté, vous pouvez naviguer en mode anonyme, mais certaines fonctionnalités peuvent être limitées.

## Structure du Projet

- **Frontend (Angular)** : Application Angular sous `/client`, tournant sur le port 4200.
- **Backend (Spring Boot)** : API Spring Boot sous `/serveur`, tournant sur le port 8080.
- **Keycloak** : Serveur d'authentification tournant sur le port 8081.

## Technologies Utilisées

- **Angular** pour le frontend
- **Spring Boot** pour le backend
- **Keycloak** pour l'authentification
- **Docker** pour le déploiement
- **Swagger** pour la documentation des API

## Utilisation de l'API

La documentation interactive des API est disponible via Swagger à l'adresse suivante : [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html).

## Gestion des Rôles et des Permissions

1. **Rôle Administrateur** :

   - Accessible via l'identifiant `admin` et le mot de passe `admin`.
   - Accès complet à toutes les fonctionnalités de l'application.

2. **Utilisateur Anonyme** :
   - Peut accéder à certaines fonctionnalités en mode lecture.
   - Limitations sur les fonctionnalités de gestion (création, modification, suppression).

## Dépendances et Configuration

- **Docker** : Assure la conteneurisation de l'application.
- **Keycloak** : Utilisé pour sécuriser l'authentification des utilisateurs et la gestion des rôles.

---

## Auteurs

- **Zine-Eddine TOUBAL** - _Développeur Full Stack_
- **Elies LARBI** - _Développeur Full Stack_

---

N'hésitez pas à apporter des améliorations et à poser des questions sur ce projet !
