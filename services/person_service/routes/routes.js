var personDict = {
    "ahRzfmVudGVycHJpc2Utc3lzdGVtc3INCxIGUGVyc29uGLNtDA1": "Jakob Pogulis",
    "ahRzfmVudGVycHJpc2Utc3lzdGVtc3INCxIGUGVyc29uGLNtDA2": "Xena",
    "ahRzfmVudGVycHJpc2Utc3lzdGVtc3INCxIGUGVyc29uGLNtDA3": "Marcus Bendtsen",
    "ahRzfmVudGVycHJpc2Utc3lzdGVtc3INCxIGUGVyc29uGLNtDA4": "Zorro",
    "ahRzfmVudGVycHJpc2Utc3lzdGVtc3INCxIGUGVyc29uGLNtDA5": "Q"
}

var appRouter = function(app){

    app.get("/list", function(req, res){
        res.status(200).json(personDict);
    });

    app.get("/find.name", function(req, res){
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

    app.get("/find.key", function(req, res){
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