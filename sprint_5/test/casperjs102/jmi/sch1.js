// run with :
// casperjs sch1.js [--dump=true] [--capture=true]

var CAPTURE = false ;
var DUMP = false ;
var RESOLUTIONS = [[1024, 768],[400, 600],[600,400]];
var APP_URL = 'http://algec.iut-blagnac.fr/iutblagnac/edt/index.html' ;
var APP_URL = 'http://localhost/edt/scheduleV23teacher.html' ;
var APP_SEM = 26

var SCHEDULE = {
	VERSION: 0, 
	innerWidth: 0,
	META: {}, 
	SERVER0: '', 
	RW_WEEK: 0, 
	CURRENT_GRP: '',
	CURRENT_GRPTP: '',
	CURRENT_SEM: '',
	LOADED_WEEKS: {}
};

var ks = [];
var links = [];
// Helpers ////////////////////////////////////
function captureResolution(resolutions) {
    resolutions.forEach(function(wh) {
     casper.then(function() {
       this.viewport(wh[0], wh[1]);
     });
     casper.then(function() {
       this.capture("scheduleview_accueil_"+wh[0]+"x"+wh[1]+".png");
       this.echo("scheduleview_accueil_"+wh[0]+"x"+wh[1]+".png saved");
     });

    });
};
function getLinks() {
    var links = document.querySelectorAll("a");
    return Array.prototype.map.call(links, function(e) {
            return e.getAttribute("href");
    });
}
// end Helpers ///////////////////////////////////

// Casper ////////////////////////////////////
var utils = require('utils');

var casper = require('casper').create({
    verbose: true,
    //logLevel: "debug"
});


casper.start(APP_URL+'#'+APP_SEM, function() {
    this.test.comment('Chargement de '+APP_URL+'#'+APP_SEM);
    this.viewport(500, 700);
    this.test.assertMatch(this.getHTML('h1#titreAccueil'),new RegExp('Sem\. '+APP_SEM), 'Semaine '+APP_SEM+' chargée');
});

casper.then(function() {
  CAPTURE = this.cli.get('capture') || CAPTURE ;
  DUMP = this.cli.get('dump') || DUMP ;
});

casper.then(function() {
	for (var i in SCHEDULE){
		SCHEDULE[i]=this.getGlobal(i);
	}
	if ( DUMP ) { utils.dump(SCHEDULE); }
});

if ( CAPTURE ) { captureResolution(RESOLUTIONS); }

casper.then(function(){
  this.test.assertTitle('EDT/INFO/View - V2.3', 'Titre Schedule OK');
});

casper.thenClick('#radioS2',function(){
       this.test.comment('Click S2');
       if ( CAPTURE ) { this.capture("scheduleview_accueil_S2.png"); }
       if ( CAPTURE ) { this.captureSelector("scheduleview_select_S2.png", '#selectgroup'); }
       if ( DUMP ) { utils.dump(this.getElementInfo('#radioS2')); }
       this.test.assertVisible('#radioS2','#radioS2 est visible');
       var tab = ['#radioG1','#radioG2','#radioG3','#radioG4']
       for(var i=0, l=tab.length;i<l;i++){
	       if ( DUMP ) { utils.dump(this.getElementInfo(tab[i])); }
	       this.test.assertVisible(tab[i],tab[i]+' est visible');
       }
       var tab = ['#radiotpGA','#radiotpGB']
       for(var i=0, l=tab.length;i<l;i++){
	       if ( DUMP ) { utils.dump(this.getElementInfo(tab[i])); }
	       this.test.assertNotVisible(tab[i],tab[i]+' est non visible');
       }
       var tab = [["CURRENT_SEM","S2"],["CURRENT_GRP","S2"],["CURRENT_GRPTP",""]]
       for(var i=0, l=tab.length;i<l;i++){
	       this.test.assertEquals(this.getGlobal(tab[i][0]),tab[i][1],tab[i][0]+' == "'+tab[i][1]+'"')
       }
});

casper.then(function(){
    ks = this.evaluate(function() {
        var elements = document.querySelectorAll('.ui-bar .tS4.hidden');
        return Array.prototype.map.call(elements, function(e) {
            return e.getAttribute('class');
        });
    });
    if ( DUMP ) { utils.dump(ks); }
    this.test.assertEquals(ks.length,30,'30 .tS4 non visibles')
});

casper.then(function(){
    ks = this.evaluate(function() {
        var elements = document.querySelectorAll('.ui-bar .tS2.hidden');
        return Array.prototype.map.call(elements, function(e) {
            return e.getAttribute('class');
        });
    });
    if ( DUMP ) { utils.dump(ks); }
    this.test.assertEquals(ks.length,0,'.tS2 tous visibles')
});

casper.thenClick('#radioG1',function(){
       this.test.comment('Click G1');
       if ( CAPTURE ) { this.capture("scheduleview_accueil_S2_G1.png"); }
       if ( CAPTURE ) { this.captureSelector("scheduleview_select_S2_G1.png", '#selectgroup'); }
       if ( DUMP ) { utils.dump(this.getElementInfo('#radioG1')); }
       this.test.assertVisible('#radioS2','#radioS2 est visible');
       var tab = ['#radioG1','#radioG2','#radioG3','#radioG4','#radiotpGA','#radiotpGB']
       for(var i=0, l=tab.length;i<l;i++){
	       if ( DUMP ) { utils.dump(this.getElementInfo(tab[i])); }
	       this.test.assertVisible(tab[i],tab[i]+' est visible');
       }
       var tab = [["CURRENT_SEM","S2"],["CURRENT_GRP","1"],["CURRENT_GRPTP",""]]
       for(var i=0, l=tab.length;i<l;i++){
	       this.test.assertEquals(this.getGlobal(tab[i][0]),tab[i][1],tab[i][0]+' == "'+tab[i][1]+'"')
       }
});

casper.thenClick('#radiotpGB',function(){
       this.test.comment('Click B');
       if ( CAPTURE ) { this.capture("scheduleview_accueil_S2_G1B.png"); }
       if ( CAPTURE ) { this.captureSelector("scheduleview_select_S2_G1B.png", '#selectgroup'); }
       if ( DUMP ) { utils.dump(this.getElementInfo('#radiotpGB')); }
       this.test.assertVisible('#radioS2','#radioS2 est visible');
       var tab = ['#radioG1','#radioG2','#radioG3','#radioG4','#radiotpGA','#radiotpGB']
       for(var i=0, l=tab.length;i<l;i++){
	       if ( DUMP ) { utils.dump(this.getElementInfo(tab[i])); }
	       this.test.assertVisible(tab[i],tab[i]+' est visible');
       }
       var tab = [["CURRENT_SEM","S2"],["CURRENT_GRP","1"],["CURRENT_GRPTP","B"]]
       for(var i=0, l=tab.length;i<l;i++){
	       this.test.assertEquals(this.getGlobal(tab[i][0]),tab[i][1],tab[i][0]+' == "'+tab[i][1]+'"')
       }
});


casper.thenClick('#radioS4',function(){
       this.test.comment('Click S4');
       if ( CAPTURE ) { this.capture("scheduleview_accueil_S4.png"); }
       if ( CAPTURE ) { this.captureSelector("scheduleview_select_S4.png", '#selectgroup'); }
       if ( DUMP ) { utils.dump(this.getElementInfo('#radioS4')); }
       this.test.assertVisible('#radioS4','#radioS4 est visible');
       var tab = ['#radioG1','#radioG2','#radioG3']
       for(var i=0, l=tab.length;i<l;i++){
	       if ( DUMP ) { utils.dump(this.getElementInfo(tab[i])); }
	       this.test.assertVisible(tab[i],tab[i]+' est visible');
       }
       var tab = ['#radioG4','#radiotpGA','#radiotpGB']
       for(var i=0, l=tab.length;i<l;i++){
	       if ( DUMP ) { utils.dump(this.getElementInfo(tab[i])); }
	       this.test.assertNotVisible(tab[i],tab[i]+' est non visible');
       }
       var tab = [["CURRENT_SEM","S4"],["CURRENT_GRP","S4"],["CURRENT_GRPTP",""]]
       for(var i=0, l=tab.length;i<l;i++){
	       this.test.assertEquals(this.getGlobal(tab[i][0]),tab[i][1],tab[i][0]+' == "'+tab[i][1]+'"')
       }
});

casper.then(function(){
    ks = this.evaluate(function() {
        var elements = document.querySelectorAll('.ui-bar .tS2.hidden');
        return Array.prototype.map.call(elements, function(e) {
            return e.getAttribute('class');
        });
    });
    if ( DUMP ) { utils.dump(ks); }
    this.test.assertEquals(ks.length,30,'30 .tS2 non visibles')
});

casper.then(function(){
    ks = this.evaluate(function() {
        var elements = document.querySelectorAll('.ui-bar .tS4.hidden');
        return Array.prototype.map.call(elements, function(e) {
            return e.getAttribute('class');
        });
    });
    if ( DUMP ) { utils.dump(ks); }
    this.test.assertEquals(ks.length,0,'.tS4 tous visibles')
});

casper.then(function(){
    if ( DUMP ) { utils.dump(casper.test.getPasses()); }
    this.test.comment(casper.test.getPasses()["cases"].length + ' asserts OK');
    this.test.comment(casper.test.getFailures()["cases"].length + ' asserts NOK');
});

casper.run(function() {
    this.test.renderResults(true);
});

// end Casper ////////////////////////////////////

//this.test.assertEquals(this.getElementInfo('#radiotpGB')["nodeName"],"input")

//~ casper.then(function() {
    //~ links = this.evaluate(getLinks);
    //~ this.echo(links.length);
//~ });
