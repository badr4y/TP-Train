# TP Train (TP 11 - 15)
_Groupe composé de FOURANE Badr, DAHECH Mohammed Amine._

## Introduction
Ce projet s'inscrit dans le cadre du cours sur les logiciels concurrents. Il consiste en la modélisation en Java du déplacement de trains sur des rails.
- Éditeur utilisé : `IntelliJ IDEA`


## Diagramme de classes final : 
![classDiagram.png](classDiagram.png)

## Observations et remarques :
- Vous pouvez voir la progression du projet en accédant aux commits au niveau du repo : https://github.com/badr4y/TP-Train
- À part les commentaires fournis avec le code initial, nous avons choisi de commenter le code en Anglais, de même pour le choix des noms des variables et méthodes.
- Nous avons changé quelques parties du code initial, plusieurs attributs de classes qui étaient finals ne le sont plus, nous avons de plus opter pour l'utilisation de List au lieu d'Arrays comme Data Structure à travers le code pour sa compatibilité avec nos besoins.
- Même si les consignes du TP nous fournissent des clés de solutions pour répondre aux problèmes confrontés au cours du développement du projet, nous avons des fois choisi de les ignorer et développer nos propres solutions.
- Nous avons trouvé que les consignes fournies au cours du TP étaient des fois difficile à comprendre et rendent à la confusion dans notre cas. Néanmoins, nous avons essayé d'être aussi fidèle que possible aux attentes de l'évaluateur.  
  - La difficulté principale qu'on a eu est lié au fait que les conditions de départ dépendait de variables non finales et donc on trouvait parfois difficile de trouver avec quoi fallait synchroniser le bout du code qui notifiait les autres threads concernés d'un changement. En fin de compte une solution facile qu'on n'a trouvé qu'à la fin et qu'on n'a pas implémenté dans cette version du projet est de boucler sur tous les elements du railway de la sorte :  
      ```java
          for (Element element : railway.elements()) {
              synchronized((element)) {
                  element.notifyAll();
              }
          }

    

## Réponses aux questions

### Roles des classes :

- **Direction :** Énumération représentant la direction du mouvement sur le chemin de fer (Railway), soit LR "de gauche à droite" ou RL "de droite à gauche".

- **Element :** Classe abstraite représentant un élément générique dans le système ferroviaire (Railway), tel qu'une section ou une station. Gère l'élément précédent, fournit des méthodes pour vérifier la disponibilité, la réservation, la libération et le mouvement.

- **Position :** Représente la position d'un train, comprenant l'élément actuel et la direction. Peut changer de direction et d'élément, et fournit une représentation sous forme de chaîne pour un affichage facile.

- **Railway :** Gère une liste d'éléments pour représenter l'ensemble du système ferroviaire. Initialise les éléments et les enregistrements, fournit une représentation sous forme de chaîne du chemin de fer.

- **Section :** Sous-classe concrète d'`Element` représentant une section du chemin de fer. Implémente des méthodes pour la disponibilité, la réservation et la libération.

- **Station :** Sous-classe concrète d'`Element` représentant une station sur le chemin de fer. Gère la taille et le nombre d'éléments, implémente des méthodes pour la disponibilité, la réservation et la libération.

- **Train :** Implémente l'interface `Runnable`, représentant un train qui se déplace le long du chemin de fer. Peut partir, se déplacer et arriver, avec des méthodes pour gérer les interruptions et fournir une représentation sous forme de chaîne.

- **BadPositionForTrainException :** Classe d'exception personnalisée jetée lorsqu'un train est initialisé avec une position invalide.

- **Main :** La classe principale où le système ferroviaire est mis en place. Crée des instances de stations, de sections et de trains, initialise le chemin de fer et lance des threads pour les trains.


### Question 2.2 : Identifiez les variables qui permettent d’exprimer l’invariant de sûreté pour la ligne de trains.
- `Railway.elements` : La liste d'éléments qui représente la ligne de trains.
- `Element.previous` : La référence à l'élément précédent dans la ligne.
- `Element.railway` : La référence au chemin de fer auquel l'élément appartient.
- `Section.full` : Un indicateur de la disponibilité d'une section (si elle est occupée par un train ou non).
- `Station.size` et `Station.count` : Le nombre de quais dans une station et le nombre actuel de trains dans cette station.

### Question 2.3 : À l’aide des variables identifiées, exprimez l’invariant de sûreté.
- **Invariant de Sûreté :**
    - Le nombre de trains maximum dans une gare (Station) est égal au nombre de quais de la gare.
    - Dans une section, il y a au maximum un train.

### Question 2.4 : Quelles sont les actions « critiques » que peut effectuer un train ?
Les actions critiques que peut effectuer un train et qui modifient l'invariant de sûreté sont principalement les méthodes associées à la gestion des mouvements, de la réservation, et de la libération des éléments de la ligne (Station, Section, etc.). Ces méthodes sont liées à la classe `Element`.

### Question 2.5 : Dans quelles classes ces actions doivent être ajoutées ?
Les actions critiques, c'est-à-dire les méthodes qui modifient l'invariant de sûreté, doivent être ajoutées dans la classe `Element` (et ses sous-classes comme `Station` et `Section`). Ces méthodes devraient être marquées comme `synchronized` pour assurer l'exclusion mutuelle et éviter les problèmes de concurrence lorsqu'elles sont exécutées par différents threads.

### Question 2.6 : Selon la méthode de construction d’une solution de synchronisation donnée plus haut, quelles autres méthodes faut-il ajouter et dans quelle classe ?**
1. **Méthodes de Réservation et Libération :**
    - Ces méthodes devraient être marquées comme `synchronized` pour assurer l'exclusion mutuelle pendant la réservation et la libération d'une section ou d'une station.
    - Exemples :
      ```java
      public synchronized void reserve() {
          // Logique de réservation
      }
 
      public synchronized void release() {
          // Logique de libération
      }
      

2. **Méthodes de Mouvement (Départ et Arrivée) :**
    - Ces méthodes devraient également être marquées comme `synchronized` pour éviter que plusieurs trains ne se déplacent simultanément et pour respecter l'invariant de sûreté.
    - Exemples :
      ```java
      public synchronized void depart(Direction dir) throws InterruptedException {
          // Logique de départ
      }
 
      public synchronized void arrive() {
          // Logique d'arrivée
      }
      

3. **Méthodes de Vérification d'État (isAvailable, isEmpty, etc.) :**
    - Ces méthodes devraient être utilisées pour évaluer les conditions d'attente lors de la synchronisation.
    - Exemples :
      ```java
      public synchronized boolean isAvailable() {
          // Logique de vérification de disponibilité
      }
 
      public synchronized boolean isEmpty() {
          // Logique de vérification d'absence de train
      }
      

4. **Méthodes de Changement d'État (changeDirection, changeElement, etc.) :**
    - Ces méthodes sont implémentées au niveau de la classe Position
    - Exemples :
      ```java
      public synchronized void changeDirection() {
          // Logique de changement de direction
      }
 
      public synchronized void changeElement() {
          // Logique de changement d'élément
      }


### Exercice 3 :  

Cet exercice traite le deadlock déclenché au cas où deux trains sont l'un devant l'autre ayant des directions opposés et chacun attend que l'autre vide sa section.  
Pour traiter ce problème, nous avons décidé d'ajouter une HashMap au niveau de la classe Railway ayant comme clés les éléments du Railway et comme valeurs respectives soit la direction d'un train dans le cas où un train est dans la section ou null dans le cas contraire.  
De cette façon, quand un train veut faire son départ d'une station, il verifie l'existence de trains allant dans le sens opposé jusqu'à le prochain train en accedant directement à la HashMap au niveau du Railway.

### Question 3.2 : À l’aide des nouvelles variables, identifiez la nouvelle condition pour l’invariant de sûreté.

La nouvelle condition pour l'invariant de sûreté peut être exprimée comme suit :

- **Nouvel Invariant de Sûreté :**
   - Un train ne peut quitter une gare que si aucune autre train n'est présent jusqu'à la prochaine station dans le sens opposé.

### Exercice 4 :

Nous n'avons pas pu résoudre et intégrer une solution fonctionnelle du deadlock dans cette version du projet. Néanmoins, nous avons une idée sur la solution possible.  
Il suffit d'implementer une methode verifyCapacity() qui vérifie si un train peut quitter une section ou une station en fonction de la direction dans laquelle il se déplace, sans dépasser la capacité maximale d'une gare intermédiaire (si elle existe). 

La méthode utilise une boucle pour parcourir les sections dans la direction donnée, en comptant le nombre de sections non vides et en sauvegardant la dernière station traversée dans la variable cross. Ensuite, elle effectue une seconde boucle pour vérifier la présence de trains dans les sections adjacentes à la gare intermédiaire, en continuant jusqu'à atteindre une nouvelle station ou la fin de la ligne. La comparaison finale est faite entre le nombre total de trains et la capacité restante de la gare intermédiaire (si elle existe). Si ces conditions sont remplies, le train peut quitter la section ou la station ; sinon, il doit attendre.  

Cela permet de s'assurer qu'aucun train ne quitte une section ou une station tant que cela pourrait entraîner un dépassement de capacité dans une gare intermédiaire située en aval.  

Voici notre implémentation de la solution qui reste toujours incomplète :  
```java
           public synchronized boolean verifyCapacity(Direction dir) {
		Element test = this.next(dir);
		int count = 1;
		
		// Iterate through the sections in the given direction
		while (test instanceof Section) {
			if (!test.isEmpty()) {
				count++;
			}
			test = test.next(dir);
		}
		
		// Save the reference to the last section (cross) in the original direction
		Element cross = test;
		
		// Continue iterating in the same direction, checking for occupied sections
		while (test instanceof Section || test.next(dir) != null) {
			test = test.next(dir);
			if (!test.isEmpty() && test.railway.getRecord().get(test) != dir) {
				count++;
			}
		}
		
		// Verify the count against the capacity of the cross station
		return count <= (((Station) cross).getSize() - ((Station) cross).getCount());
	}

```

## Guide d'utilisation 

Ce guide vous expliquera comment créer et utiliser un système ferroviaire basé sur le modèle fourni.

### Création du système ferroviaire

1. Commencez par créer les éléments de votre système ferroviaire. Vous pouvez utiliser les classes `Station` et `Section` pour cela. Voici un exemple :

    ```java
    // Création des stations
    Station stationA = new Station("StationA", 3);
    Station stationB = new Station("StationB", 2);
    Station stationC = new Station("StationC", 3);

    // Création des sections
    Section sectionAB = new Section("AB");
    Section sectionBC = new Section("BC");
    Section sectionCD = new Section("CD");
    Section sectionMN = new Section("MN");
    Section sectionNP = new Section("NP");
    

2. Ensuite, créez une liste d'éléments représentant votre chemin de fer. Ajoutez les éléments dans l'ordre souhaité :

    ```java
    List<Element> elements = new ArrayList<>(Arrays.asList(
        stationA, sectionAB, sectionBC, sectionCD, stationB, sectionMN, sectionNP, stationC
    ));
    

3. Utilisez la liste d'éléments pour créer une instance de la classe `Railway` :

    ```java
    Railway railway = new Railway(elements);
    

4. Affichez le chemin de fer pour vérifier qu'il a été correctement créé :

    ```java
    System.out.println("Le chemin de fer est :");
    System.out.println("\t" + railway);
    

## Création des trains et démarrage du système

1. Créez des positions initiales et finales pour vos trains. Utilisez ces positions pour instancier les trains :

    ```java
    Position initialPosition = new Position(stationA, Direction.LR);

    Train train1 = new Train("Train1", initialPosition);
    Train train2 = new Train("Train2", initialPosition);
    Train train3 = new Train("Train3", initialPosition);
    

2. Configurez le nombre de trains dans les stations :

    ```java
    stationA.setCount(3);
    

3. Créez et démarrez les threads pour chaque train :

    ```java
    Thread thread1 = new Thread(train1);
    Thread thread2 = new Thread(train2);
    Thread thread3 = new Thread(train3);

    thread1.start();
    thread2.start();
    thread3.start();
    

4. Gérez les éventuelles exceptions liées à la mauvaise position des trains :

    ```java
    try {
        // Création des trains et démarrage des threads
    } catch (BadPositionForTrainException e) {
        System.out.println("Le train " + e.getMessage());
    }
    

C'est tout ! Vous avez maintenant créé un système ferroviaire avec des éléments et des trains. N'hésitez pas à ajuster les positions, les nombres de trains, et autres paramètres selon vos besoins.
