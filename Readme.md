# The Wild West Bounty Hunter Network

**Language:** Java

**Project Description:**

In the rugged and lawless Wild West, where outlaws and bandits roam freely, there exists a network of fearless bounty hunters. These bounty hunters take contracts to capture or eliminate notorious outlaws with bounties on their heads. Inspired by the Wild West, your challenge is to create a backend system for the Wild West Bounty Hunter Network (WWBHN).

**Entities:**

1. **Bounty Hunter:** Represents a bounty hunter in the network. Each bounty hunter has a cowboy name, origin, reputation, and an inventory of weapons and equipment.

2. **Bounty Contract:** Represents a contract posted by a town, sheriff, or private individual looking to hire a bounty hunter to capture or eliminate a wanted outlaw. Each contract has a poster's name, a reward amount, outlaw name, outlaw description, and last known location.

3. **Town:** Represents a town in the Wild West. Each town has a name, description, and a list of saloons where bounty hunters can gather, trade equipment, and access contract postings.

4. **Saloon:** Represents a saloon in a town where bounty hunters can meet, socialize, and access contract postings. Each saloon has a name and location.

**Functionality:**

1. **Bounty Hunter Management:**
   - Create, read, update, and delete bounty hunters.
   - View a list of all bounty hunters.
   - Search for bounty hunters by name, origin, or reputation.

2. **Bounty Contract Management:**
   - Create, read, update, and delete bounty contracts.
   - View a list of all available contracts.
   - Filter contracts by reward amount, outlaw name, or last known location.
   - Assign a contract to a bounty hunter.

3. **Town and Saloon Management:**
   - Create, read, update, and delete towns and saloons.
   - View a list of all towns and their saloons.

4. **Inventory Management:**
   - Bounty hunters can add, remove, or update items in their inventory.
   - Each item has a name, description, and type (e.g., weapon, armor, whiskey).

5. **Reputation System:**
   - Implement a reputation system where a bounty hunter's reputation changes based on their success or failure in completing contracts.

**Technologies:**

- **Programming Language:** Java
- **Framework:** Spring Boot
- **Database:** PostgreSQL
- **REST API:** Spring Web
- **ORM:** Spring Data JPA
- **Authentication:** Spring Security
- **Documentation:** Swagger for API documentation

**Target Technology/Concept:**

- **Spring Boot**: Focus on creating a well-structured Spring Boot application with proper separation of concerns and clear RESTful API endpoints.

**Project Architecture:**

Build the project as a **monolith** initially to keep it simple. You can later explore breaking it into microservices if you wish to expand the project further.

**Challenges:**

1. Implement a notification system that alerts bounty hunters when a new contract matching their criteria is posted.
2. Integrate a JWT-based authentication system to secure API endpoints and allow bounty hunters to log in and manage their profiles.
3. Develop a reputation system that calculates and updates the reputation of bounty hunters based on the success of their completed contracts.
4. Optimize the database schema for performance, considering potential scalability in the future.
5. Add unit tests and integration tests to ensure the reliability of your application.
6. Implement rate limiting and throttling to prevent abuse of the API.
7. Create a feature for bounty hunters to track their progress on active contracts.

This project will challenge your Java backend development skills while immersing you in the Wild West theme, allowing you to create a unique and engaging system for bounty hunters in the Old West. Saddle up and get ready for a wild ride in the Wild West Bounty Hunter Network!

