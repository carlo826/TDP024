var bankDict = {
    1: "SWEDBANK",
    2: "IKANOBANKEN",
    3: "JPMORGAN",
    4: "NORDEA",
    5: "CITIBANK",
    6: "HANDELSBANKEN",
    7: "SBAB",
    8: "HSBC",
    9: "NORDNET",
}

var appRouter = function(app){

    app.get("/bank/list", function(req, res){
        res.status(200).json(bankDict);
    });

    app.get("/bank/find.name", function(req, res){
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

    app.get("/bank/find.key", function(req, res){
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