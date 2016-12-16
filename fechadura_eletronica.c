#include <EEPROM.h>
#define confirma 7
#define muda_senha 6
#define led_verde 8

int senha[4] = {0, 0, 0, 0};
int cont = 0;
char reset_senha = false;
char nova_senha = false;
uint8_t bytereceived = 0;

void setup() {

	Serial.begin(9600);
	// inicializando os pinos
	pinMode(confirma, INPUT);
	pinMode(muda_senha, INPUT);
	pinMode(led_verde, OUTPUT);
	
	// caso não tenha nada gravado na memória à senha padrão será gravada na mesma.
	if(EEPROM.read(0) == 0){	
		for(int i = 0; i < 4; i++){

			EEPROM.write(i, '0');

		}
		Serial.println("Senha Padrão Gravada.");
	}

}

void loop() {

	resetPassword();

	newPassword();

	start();

	if(!digitalRead(muda_senha)){

		nova_senha = true;

	}

}






void resetPassword(){
	if(reset_senha){

		for(int i = 0; i < 4; i++){

			EEPROM.write(i, '0');

		}

	}

	reset_senha = false;
}

void newPassword(){
	if(nova_senha){
		Serial.println("Digite a nova Senha.");

		for(int i = 0; i < 4; i++){

			recebeDadosSerial();
			EEPROM.write(i, bytereceived);
			
		}

		Serial.println("Senha Trocada.");		
	}

	nova_senha = false;
}


void recebeDadosSerial(){
	cont = 0;
	bytereceived = 0;
	//aguarda dados na porta serial por 20s
	while(!Serial.available()){
		if(cont > 200){
			break;
		}
		cont++;
		delay(100);
	}

	//armazena e mostra a opção escolhida
	if(Serial.available() > 0){
		bytereceived = Serial.read();
		Serial.write(bytereceived);
		Serial.println();
	}

}

void start(){

	if(!digitalRead(confirma)){

		Serial.println("digite sua Senha:");

		for(int i = 0; i < 4; i++){
			
			recebeDadosSerial();
			senha[i] = bytereceived;

		}

		testeSenha();

	}
}

void testeSenha(){
	cont = 0;

	for(int i = 0; i < 4; i++){

		if(senha[i] == EEPROM.read(i)){
			cont++;
		}else{
			Serial.println("Senha Errada");
			break;
		}

	}

	if(cont == 4){
	
		Serial.println("Acesso Permitido.");
		digitalWrite(led_verde, HIGH);
		delay(500);
		digitalWrite(led_verde, LOW);
	}
		
}