/* fichier généré par FunArduino v.bétâ 0.1 */

// Les librairies:

// Déclaration des variables
	int etat=0;


void setup(){
	pinMode(2,OUTPUT);
}

void loop(){
	digitalWrite(2,HIGH);
	if(etat==42){
		digitalWrite(2,HIGH);
	}
	delay(500);
	digitalWrite(2,LOW);
	delay(500);
}

