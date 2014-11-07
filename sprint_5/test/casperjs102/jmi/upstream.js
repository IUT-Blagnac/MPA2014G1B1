var CAPTURE = true ;

var casper = require("casper").create({
    logLevel: "debug"
});

casper.start('http://google.fr/', function() {
    this.viewport(1024, 768);
    if ( CAPTURE ) { this.capture("ggl_search.png"); }
    this.fill('form[action="/search"]', {
        q: "upstream university"
    }, true);
});

casper.then(function() {
    this.test.assertTitle("upstream university - Recherche Google", "google title is ok");
    if ( CAPTURE ) { this.capture("ggl_results.png"); }
});

casper.thenOpen("http://upstream-university.org/", function() {
    this.test.assertTitle("Upstream University - Home", "Upstream University - Home");
    if ( CAPTURE ) { this.capture("upstream_home.png"); }
});

casper.run(function() {
    this.test.renderResults(true);
});

