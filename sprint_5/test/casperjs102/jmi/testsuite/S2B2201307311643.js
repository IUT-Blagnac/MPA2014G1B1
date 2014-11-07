// run with :
// casperjs test testsuite/  [--dump=true] [--capture=true]
// casperjs test testsuite/S1B1YYYYMMDDHHMN.js [--dump=true] [--capture=true]

var CAPTURE = false ;
var DUMP = false ;
var RESOLUTIONS = [[1024, 768],[400, 600],[600,400]];
var BACKLOG = "Page d'accueil/Selection semaine"
var APP_URL = 'http://algec.iut-blagnac.fr/iutblagnac/edt/index.html' ;
var APP_URL = 'http://localhost/moodle/schedule/scheduleV24teacher.html' ;
var APP_SEM = 36

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

casper.then(function(){
  this.test.assertTitle('EDT/INFO/View - V2.4', 'Titre Schedule OK');
  this.test.comment('FIRST_PLANNED_WEEK = '+SCHEDULE["META"]["FIRST_PLANNED_WEEK"]);
  this.test.comment('LAST_PLANNED_WEEK = '+SCHEDULE["META"]["LAST_PLANNED_WEEK"]);
});

casper.then(function() {
  this.test.assertVisible('#busydefault', '"Dispos par défaut" button visible');
  this.test.assertVisible('#loginformbutton', '"Login" button visible');
  this.test.assertVisible('#weeknavbarTop', '"weeknavbarTop" buttons visible');
  this.test.assertVisible('#weeknavbarBottom', '"weeknavbarBottom" buttons visible');
  this.test.assertVisible('#searchTeacher', '"searchTeacher" field visible');
  this.test.assertNotVisible('#selectgroup', '"selectgroup" buttons not visible');
});

casper.thenClick('#weeknavbarTop .weekprevious',function(){
    this.test.comment('Click <');
    if ( CAPTURE ) { this.capture("scheduleview_accueil_previous.png"); }
    if ( CAPTURE ) { this.captureSelector("scheduleview_weeknavbarTop_previous.png", '#weeknavbarTop'); }
    if ( DUMP ) { utils.dump(this.getElementInfo('#weeknavbarTop .weekprevious')); }
    this.test.assertMatch(this.getHTML('h1#titreAccueil'),new RegExp('Sem\. '+(APP_SEM - 1)), 'Semaine '+(APP_SEM - 1)+' chargée');
});

casper.then(function() {
 var el_w_m = [['.weekcurrent',APP_SEM,'Click ='],
  ['.weeknext',APP_SEM+1,'Click >'],
  ['.weeknext',APP_SEM+2,'Click >'],
  ['.weekprevious',APP_SEM+1,'Click <'],
  ['.weekprevious',APP_SEM,'Click <'],
  ['.weeklast',SCHEDULE["META"]["LAST_PLANNED_WEEK"],'Click >>|'],
  ['.weeknext',SCHEDULE["META"]["LAST_PLANNED_WEEK"]+1,'Click >'],
  ['.weeknext',SCHEDULE["META"]["LAST_PLANNED_WEEK"]+2,'Click >'],
  ['.weekfirst',SCHEDULE["META"]["FIRST_PLANNED_WEEK"],'Click |<<'],
  ['.weekprevious',SCHEDULE["META"]["FIRST_PLANNED_WEEK"]-1,'Click <'],
  ['.weekprevious',SCHEDULE["META"]["FIRST_PLANNED_WEEK"]-2,'Click <'],
  ['.weekcurrent',APP_SEM,'Click =']
 ] ;

 casper.each(el_w_m, function(self, sel) {
  casper.thenClick('#weeknavbarTop '+sel[0],function(){
    this.test.comment(sel[2]);
    this.test.assertMatch(this.getHTML('h1#titreAccueil'),new RegExp('Sem\. '+sel[1]), 'Semaine '+sel[1]+' chargée');
    var year = this.getGlobal("YEAR");
    var events = this.getGlobal("LOADED_WEEKS")[sel[1]];
    if ( DUMP ) { utils.dump(events); }
    if (events && events[0]) {
	    this.test.assertMatch(events[0],new RegExp(year+'_'+sel[1]), 'Evênement 0 match /'+year+'_'+sel[1]+'/');
	    var re = new RegExp(year+'_'+sel[1]+'_'+'([^_]+)_'+'[^_]+_'+'([^_]+)_'+'[^_]+_'+'([^_]+)_'+'([^_]+)_'+'[^_]+_'+'([^_]+)')
	    var m = re.exec(events[0]);
	    var dks = this.getElementInfo('#'+m[1]+' .t'+m[2]);
	    if ( DUMP ) { utils.dump(dks); }
	    var re2 = new RegExp(m[3]+'.*[\n\t]'+m[4]+'.*[\n\t]0*'+m[5]+'[\n\t]');
	    this.test.assertMatch(dks["text"],re2,'Evênement 0 = '+m[3]+'-'+m[4]+'-'+m[5]);
	    this.test.assertMatch(events[events.length - 1],new RegExp(year+'_'+sel[1]), 'Evênement '+(events.length - 1)+' match /'+year+'_'+sel[1]+'/');
	    var m = re.exec(events[events.length - 1]);
	    var dks = this.getElementInfo('#'+m[1]+' .t'+m[2]);
	    if ( DUMP ) { utils.dump(dks); }
	    var re2 = new RegExp(m[3]+'.*[\n\t]'+m[4]+'.*[\n\t]0*'+m[5]+'[\n\t]');
	    this.test.assertMatch(dks["text"],re2,'Evênement '+(events.length - 1)+' = '+m[3]+'-'+m[4]+'-'+m[5]);
    }
  });
 });
});

       
casper.run(function() {
    this.test.renderResults(true);
    this.test.done(15);
});

// end Casper ////////////////////////////////////
