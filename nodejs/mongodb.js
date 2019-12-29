var mongoose = require('mongoose');
mongoose.connect("mongodb+srv://ahn9807:wnsgh8546**@cluster0-7lhcw.mongodb.net/test");
var db = mongoose.connection;

db.on('error', function() {
	console.log('Connection Failed!');
});

db.once('open', function() {
	console.log('Connected!');
});

var information = mongoose.Schema({
	name : 'string',
	phone : 'string'
});

var Info = mongoose.model('Schema',information);

var debugInfo = new Info({name:'Ahn Jun Ho', phone:'01031241057'});

debugInfo.save(function(error, data) {
	if(error) {
		console.log(error);
	}
	else {
		console.log('saved');
	}
});

Info.find(function(error, informations) {
	console.log('Read all');
		if(error) {
		console.log(error);
	}
	else {
		console.log(informations);
	}
});





















