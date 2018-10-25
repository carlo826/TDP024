var express = require("express");
var parser = require("body-parser");
var routes = require("./routes/routes.js");
var app = express();

app.use(parser.json());

routes(app);

var server = app.listen(8060, function(){
    console.log("Person service running on port: ", server.address().port);
});