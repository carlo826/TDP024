var bankDict = {
    "ahRzfmVudGVycHJpc2Utc3lzdGVtc3ILCxIEQmFuaxiJJww1": "SWEDBANK",
    "ahRzfmVudGVycHJpc2Utc3lzdGVtc3ILCxIEQmFuaxiJJww2": "IKANOBANKEN",
    "ahRzfmVudGVycHJpc2Utc3lzdGVtc3ILCxIEQmFuaxiJJww3": "JPMORGAN",
    "ahRzfmVudGVycHJpc2Utc3lzdGVtc3ILCxIEQmFuaxiJJww4": "NORDEA",
    "ahRzfmVudGVycHJpc2Utc3lzdGVtc3ILCxIEQmFuaxiJJww5": "CITIBANK",
    "ahRzfmVudGVycHJpc2Utc3lzdGVtc3ILCxIEQmFuaxiJJww6": "HANDELSBANKEN",
    "ahRzfmVudGVycHJpc2Utc3lzdGVtc3ILCxIEQmFuaxiJJww7": "SBAB",
    "ahRzfmVudGVycHJpc2Utc3lzdGVtc3ILCxIEQmFuaxiJJww8": "HSBC",
    "ahRzfmVudGVycHJpc2Utc3lzdGVtc3ILCxIEQmFuaxiJJww9": "NORDNET",
}

var appRouter = function(app){

    app.get("/list", function(req, res){
        res.status(200).json(bankDict);
    });

    app.get("/find.name", function(req, res){
        var found = false;
        var bankName = req.param("name");
        for (var key in bankDict){
            if (bankDict[key] == bankName){
                found = true;
                res.status(200).send(key);
            }
        }
        if(!found)
            res.status(204).send('null');
    });

    app.get("/find.key", function(req, res){
        var found = false;
        var bankKey = req.param("key");
        for (var key in bankDict){
            if(key == bankKey){
                found = true;
                res.status(200).send(bankDict[key]);
            }
        }
        if(!found)
            res.status(204).send('null');
    });
}

module.exports = appRouter;