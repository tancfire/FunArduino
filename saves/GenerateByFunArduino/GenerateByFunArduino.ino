/* fichier généré par FunArduino v.bétâ 0.1 */

// Déclaration des variables
	int etat=0;


void setup(){
	pinMode(3,OUTPUT);
}

void loop(){
	digitalWrite(3,HIGH);
	delay(500);
	digitalWrite(3,LOW);
	delay(500);
	if(etat>42){
	}
}

