# WebRoguelike

A roguelike but its on the web.

If compiling from source
Compile maven with clean install directives


On first run, in terminal cd into the webapp folder
Default:
cd src/main/webapp/ 

And run
npm install -g npm@latest 
npm i
npm run serve

And run the jar file in another terminal 
java -jar WebRoguelike-1.0.jar

Spring occupies port 4201, and Vue uses 4200


When finished, the node modules can be removed from src/main/webapp with
rm -rf node_modules