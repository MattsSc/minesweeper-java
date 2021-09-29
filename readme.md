# Welcome to Minesweeper

## Documentation

**_Swagger:_** https://minesweeper-java-sc.herokuapp.com/api/minesweeper/swagger-ui/

## Notes

I use Java 14 and Spring boot to make this application. This project is separate in 4 layers:
* Api: Request and responses for the application services.
* Controller: Entry points of the project.
* Domain: Domain models. 
* Service: Is the connection between the controller and domain.

## Model decision

Some decisions I made on the road:
* The game starts when its created
* When you mark a cell (or unmark) does not count as a move. :D
* The first move could be a bomb (https://www.reddit.com/r/AskReddit/comments/djnck/minesweeper_debate_can_you_can_lose_on_the_first/)





