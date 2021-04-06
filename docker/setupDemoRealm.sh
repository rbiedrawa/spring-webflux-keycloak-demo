#!/bin/sh

realmName="demo"
client_id="spring_keycloak_demo_client"
client_secret="9ee1dcb4-4983-4ee5-b8a2-ae384f6fc4e5"

echo "Starting creation of $realmName realm. Your clientId: $client_id clientSecret: $client_secret"

# login
docker-compose exec keycloak /opt/jboss/keycloak/bin/kcadm.sh config credentials --server http://localhost:8080/auth --realm master --user admin --password admin

# Create realm
docker-compose exec keycloak /opt/jboss/keycloak/bin/kcadm.sh create realms -s realm=$realmName -s enabled=true

# Create 'MESSAGES:READ' scope and client
docker-compose exec keycloak /opt/jboss/keycloak/bin/kcadm.sh create -x "client-scopes" -r $realmName -s name=MESSAGES:READ -s protocol=openid-connect
docker-compose exec keycloak /opt/jboss/keycloak/bin/kcadm.sh create clients -r $realmName -s clientId=$client_id -s enabled=true -s clientAuthenticatorType=client-secret -s secret=$client_secret  -s 'redirectUris=["*"]' -s 'defaultClientScopes=["MESSAGES:READ", "web-origins", "profile", "roles", "email"]'

# create realm roles
docker-compose exec keycloak /opt/jboss/keycloak/bin/kcadm.sh create roles -r $realmName -s name=ROLE_USER -s 'description=Regular user with limited set of permissions'
docker-compose exec keycloak /opt/jboss/keycloak/bin/kcadm.sh create roles -r $realmName -s name=ROLE_ADMIN -s 'description=Admin user unlimited access'

# Create demoUser, assign password, role and scope
docker-compose exec keycloak /opt/jboss/keycloak/bin/kcadm.sh create users -r $realmName -s username=demo_user -s enabled=true
docker-compose exec keycloak /opt/jboss/keycloak/bin/kcadm.sh set-password -r $realmName --username demo_user --new-password demo_user
docker-compose exec keycloak /opt/jboss/keycloak/bin/kcadm.sh add-roles --uusername demo_user --rolename ROLE_USER -r $realmName

# Create demoAdmin, assign password, role and scope
docker-compose exec keycloak /opt/jboss/keycloak/bin/kcadm.sh create users -r $realmName -s username=demo_admin -s enabled=true
docker-compose exec keycloak /opt/jboss/keycloak/bin/kcadm.sh set-password -r $realmName --username demo_admin --new-password demo_admin
docker-compose exec keycloak /opt/jboss/keycloak/bin/kcadm.sh add-roles --uusername demo_admin --rolename ROLE_ADMIN --rolename ROLE_USER -r $realmName
