#include <EEPROM.h>
#include <SoftwareSerial.h>
#include <Stepper.h>
//comandos recebidos via bluetooth
#define ok 111
#define muda_senha 109
#define fechar_porta 102
// variáveis utilizadas para identificação dos pinos
#define led_vermelho 4
#define led_verde 5
#define buzzer 6


void start(void);
void recebeDados(void);
void newPassword(void);
void fecharPorta(void);
void esvaziaBuffer(void);


const int stepsPerRevolution = 500; // Passos por volya
int senha[4] = {0, 0, 0, 0};
int cont = 0;
char portaAberta = true;
char portaFechada = false;
uint8_t bytereceived = 0;
SoftwareSerial bluetooth(2, 3); // RX, TX
Stepper myStepper(stepsPerRevolution, 10, 11, 8, 9); // Declaração do motor

void setup() {

	Serial.begin(9600);
	bluetooth.begin(9600);

	// inicializando os pinos
	pinMode(led_verde, OUTPUT);
	pinMode(led_vermelho, OUTPUT);
	pinMode(buzzer, OUTPUT);

	// caso não tenha nada gravado na memória à senha padrão será gravada na mesma.
	if(EEPROM.read(0) == 0){  
		for(int i = 0; i < 4; i++){

			EEPROM.write(i, '0');

		}
		
		Serial.println("Senha Padrão Gravada.");

	} else {
		
		Serial.println("Senha Existe");
		
	}

}

void loop() {

	recebeDados();

	newPassword();

	start();

	fecharPorta();

}

void recebeDados(){
	cont = 0;
	bytereceived = 0;
	// aguarda dados por 20s
	while(!bluetooth.available()){
		if(cont > 200){
			break;
		}
		cont++;
		delay(100);
	}

// armazena o que está no buffer e nos mostra o que foi armazenado
	if(bluetooth.available() > 0){
		bytereceived = bluetooth.read();
		Serial.write(bytereceived);
		Serial.println();
	}

}

void newPassword(){
	if(bytereceived == muda_senha){
		
		esvaziaBuffer();

		// vetor necessário para verificar se a nova senha foi inserida corretamente
		int confirma_senha[4] = {0, 0, 0, 0};

		Serial.println("Digite Sua Senha.");

		// buzzer é ligado para avisar o usuário para digitar senha atual
		tone(buzzer, 1500);
		delay(1000);
		notone(buzzer);

		for(int i = 0; i < 4; i++){

			recebeDados();
			senha[i] = bytereceived;

		}

		esvaziaBuffer();
		testeSenha();

		if(cont == 4){

			// caso a senha seja correta o usuário poderá digitar a nova senha
			digitalWrite(led_verde, HIGH);
			tone(buzzer, 1500);
			delay(1000);
			notone(buzzer);

			Serial.println("Digite a nova Senha.");

			for(int i = 0; i < 4; i++){

				recebeDados();
				senha[i] = bytereceived;
			
			}

			esvaziaBuffer();

			tone(buzzer, 1500);
			delay(1000);
			notone(buzzer);

			Serial.println("Confime sua senha.");

			for(int i = 0; i < 4; i++){

				recebeDados();
				confirma_senha[i] = bytereceived;
			      
			}

			cont = 0;

			for(int i = 0; i < 4; i++){

				if(senha[i] == confirma_senha[i]){

					cont++;

				}else{

					Serial.println("Senhas Diferentes, Repita o Processo.");
					digitalWrite(led_vermelho, HIGH);
					delay(1000);
					digitalWrite(led_vermelho, LOW);
					esvaziaBuffer();
					break;

				}
			}

			if(cont == 4){
				for(int i = 0; i < 4; i++){

					EEPROM.write(i, senha[i]);

				}
				Serial.println("Senha Trocada.");
				digitalWrite(led_verde, LOW);
				esvaziaBuffer();
			}
		}
	}
}

void fecharPorta(){

	if(bytereceived == fechar_porta){

		if(portaAberta){

			myStepper.step(-498);
			portaAberta = false;
			portaFechada = true;

		}

	}
}



void start(){

	if(bytereceived == ok){

		esvaziaBuffer();
		Serial.println("digite sua Senha:");
		digitalWrite(led_verde, HIGH);
		digitalWrite(led_vermelho, HIGH);

		for(int i = 0; i < 4; i++){

			recebeDados();
			senha[i] = bytereceived;

		}

		digitalWrite(led_verde, LOW);
		digitalWrite(led_vermelho, LOW);
		esvaziaBuffer();
		testeSenha();

		if(cont == 4){

			digitalWrite(led_verde, HIGH);
			delay(1000);
			digitalWrite(led_verde, LOW);

			if(portaFechada){

				myStepper.step(498);
				portaAberta = true;
				portaFechada = false;
				
			}

		}

	}
}

void testeSenha(){
	cont = 0;

	for(int i = 0; i < 4; i++){

		if(senha[i] == EEPROM.read(i)){
		
			cont++;
		
		}else{
			
			Serial.println("Senha Errada");
			digitalWrite(led_vermelho, HIGH);
			delay(1000);
			digitalWrite(led_vermelho, LOW);
			break;
		
		}

	}

}

void esvaziaBuffer(){

	//evita que algum digito a mais seja lido evitando erros por falta de atenção
	while(bluetooth.available() != 0){
		bluetooth.read();
	}

}