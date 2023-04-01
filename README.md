# Broadcaster
Broadcaster is a Telegram bot designed to aggregate articles/posts on specific topics from various media resources, allowing users to subscribe to topics and receive notifications when new posts are available. The goal of this project is to reduce the time it takes to search for articles/posts on given topics.

## Features
- Users can add, remove, and check topics.
- The bot supports different media resources for retrieving posts, including RSS feed and API-based media resources.
- The bot checks all supported media resources in an async scheduled way to see if new posts are available and propagates/publishes this event to a queue.
- The bot has a QueueProcessor service that handles all events and sends messages to all users related to the topic when a new post is available.

## Usage
To reach bot:
https://t.me/broadycasterbot

Once the bot is running, users can interact with it by sending commands. The following commands are available:
- /add_topic <topic> - Subscribe to a topic.
- /rm_topic <topic> - Unsubscribe from a topic.
- /show_topic - List all subscribed topics.
- /available_media - Show available media resources.

## Architecture
The architecture of the project consists of a DAO layer with entities for the User and Topic domain models. The bot checks for new posts in an async scheduled way and publishes events to a queue. The QueueProcessor service handles all events and sends messages to all users related to the topic when a new post is available.

![11111111](https://user-images.githubusercontent.com/72043323/229279136-2007bf8e-8671-4888-aa09-754353648a0e.png)


## Technologies
The project uses the following technologies:

- Java
- Spring Framework
- Hibernate
- Docker
- MySQL
