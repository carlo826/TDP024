var personDict = {
    1: "Jakob Pogulis",
    2: "Xena",
    3: "Marcus Bendtsen",
    4: "Zorro",
    5: "Q"
}

var appRouter = function(app){

    app.get("/perosn/list", function(req, res){
        res.status(200).json(personDict);
    });

    app.get("/person/find.name", function(req, res){
        var personName = req.param("name");
        var resultDict = {};
        var found = false;
        for (var key in personDict){
            if (personDict[key] == personName){
                found = true;
                resultDict[key] = personName;
            }
        }
        if(found)
            res.status(200).send(resultDict);
        else
            res.status(204).send(resultDict);
    });

    app.get("/person/find.key", function(req, res){
        var personKey = req.param("key");
        for (var key in personDict){
            if(key == personKey){
                res.status(200).send(personDict[key]);
            }
        }
        res.status(204).send('null');
    });
}



module.exports = appRouter;