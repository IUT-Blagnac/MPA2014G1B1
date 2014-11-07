var CAPTURE = true ;

var casper = require("casper").create({
    logLevel: "debug"
});

casper.start("https://www.google.com/calendar/render", function() {
    this.viewport(1024, 768);
    this.test.assertTitle("Google Agenda", "Google Agenda homepage");
    if ( CAPTURE ) { this.capture("ggllogin.png"); }

    this.test.assertExists('form[action="https://accounts.google.com/ServiceLoginAuth"]', "main form found");
    this.fill('form[action="https://accounts.google.com/ServiceLoginAuth"]', {
        'Email': "jeanmichel.inglebert@gmail.com",
	'Passwd': "jmigmail",
	'PersistentCookie': false
    }, false);
});

casper.thenClick('#signIn',function() {
    if ( CAPTURE ) { this.capture("ggla.png"); }
    //~ this.test.assertTitle("foo - Recherche Google", "google title is ok");
    //~ this.test.assertUrlMatch(/q=foo/, "search term has been submitted");
    //~ this.test.assertEval((function() {
        //~ return __utils__.findAll("h3.r").length >= 10;
    //~ }), "google search for \"foo\" retrieves 10 or more results");
});

casper.thenClick('#clst_fav_menu',function() {
    if ( CAPTURE ) { this.capture("gglmenu.png"); }
});

casper.run(function() {
    this.test.renderResults(true);
});

//menu?
//<span class="calHeaderSpace">Autres agendas</span><span id="clst_fav_menu" class="clstMenu to-disable to-disable-tabindex" tabindex="0" role="menu">Autres options...</span>
//<form autocomplete="off" class="gc-dialogbody">
//input name=url
//<button type="button" name="add" class="gc-dialogbold">Ajouter</button>