var express = require("express");
var parser = require("body-parser");
var routes = require("./routes/routes.js");
var app = express();

app.use(parser.json());

routes(app);

var server = app.listen(8070, function(){
    console.log("Bank service running on port: ", server.address().port);
});