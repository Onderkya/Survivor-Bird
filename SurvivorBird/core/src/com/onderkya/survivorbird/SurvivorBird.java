package com.onderkya.survivorbird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;



import java.awt.Color;
import java.util.Random;

import javax.print.attribute.standard.Media;
import javax.xml.soap.Text;

import sun.java2d.SurfaceDataProxy;

import static java.awt.Color.BLACK;
import static java.awt.Color.green;

public class SurvivorBird extends ApplicationAdapter {


	SpriteBatch batch;

	// Oyundaki Tüm Objelerimizi yaratmak için Texture'dan obje oluşturduk.
	Texture background;
	Texture bird;
	Texture bee;
	Texture bee2;
	Texture bee3;
	Texture Green;
	Texture Red;
	Texture bird2;

	float bird_x=0;
	float bird_y=0;
	int gamestate=0; // Oyun başladımı kontrol ediceğiz..
	float velocity=0; //Hız için yazdık..
	float gravity=0.4f;  // Yer çekimi için yaptık velocity'i ayarlamak için aslında..

	Random random;

	int score=0;
	int score_bee=0;
  BitmapFont font_score;
  BitmapFont font_GameOver;
  BitmapFont font_GameOver_Score;


  //ShapeRenderer shapeRenderer;

 // Seslerimizi Oluşturduk.
  Sound sound_bee;
  Sound sound_background;
  Sound sound_coin;


  Circle redCircle;
  Circle[] greenCircle;



  float yem_velocity=3;

   //Circle'larımızı oluşturduk..
	Circle birdCircle;

	Circle[] beeCircles;
	Circle[] beeCircles2;
	Circle[] beeCircles3;


   // bee_X içerisinde kaç adet Arıların  "X" koordinatı olucağını belirledik..
	int numberOf_bee=4; // Her seferinde 4 lü arı getirilecek..
	float[] bee_X=new float[numberOf_bee];
	float[]  green_X=new float[numberOf_bee];

	//Y Ekseni için oluşturduk..
	float[] beeOffset= new float[numberOf_bee];
	float[] beeOffset2= new float[numberOf_bee];
	float[] beeOffset3= new float[numberOf_bee];


	float[] greenOffset=new float[numberOf_bee];


	//Mesafemizi ve Arının X Ekseninde Nasıl bir Hızla Negatif Pozisyonda gideceğini ayarladık.

	float distance=0;
	float bee_velocity=3;
	float Bird_Genislik;
	float Bird_Yukseklık;


	int yesil_skor=0;
	boolean Yesil_durum=false;
	int score_bee2=0;





	@Override
	public void create () {


		Bird_Genislik=Gdx.graphics.getWidth()/12;
		 Bird_Yukseklık=Gdx.graphics.getHeight()/10;

		//oyun açılınca kuşumuzu ve arılarımızın resimlerini ekledik..

		batch=new SpriteBatch();
		background=new Texture("background.png"); // buraya objemizi veriyoruz..
		bird=new Texture("bird.png"); // Kuşumuzu Burada ekliyoruz..
		bird2=new Texture("bird.png");
		bee=new Texture("bee.png");
		bee2=new Texture("bee.png");
		bee3=new Texture("bee.png");
		Green=new Texture("yesıl.png");
	    Red=new Texture("kırmızı.png");

		//Score ekranda yazdırmak için
		font_score=new BitmapFont();
		font_score.setColor(com.badlogic.gdx.graphics.Color.WHITE);
		font_score.getData().setScale(5);

		//GameOver Yazısı
		font_GameOver=new BitmapFont();
		font_GameOver.setColor(com.badlogic.gdx.graphics.Color.WHITE);
		font_GameOver.getData().setScale(6);

		//Oyun Bittiğinde Kişinin Skorunu Göstereceğiz..
		font_GameOver_Score=new BitmapFont();
		font_GameOver_Score.setColor(com.badlogic.gdx.graphics.Color.BLACK);
		font_GameOver_Score.getData().setScale(7);


		// Arımızın ve Arkaplanımızın Seslerini burada tanıttık ve döngü içerisine aldık.
		//sound_bee=Gdx.audio.newSound(Gdx.files.internal("beeVoice.mp3"));
		sound_background=Gdx.audio.newSound(Gdx.files.internal("Back-ground.mp3"));
		sound_coin=Gdx.audio.newSound(Gdx.files.internal("coin.mp3"));

		//long id=sound_bee.play(2f);
		//sound_background.play(4f);
		//long id2=sound_background.play(4f);
		//sound_background.setLooping(id2,true);
		//sound_background.setLooping(sound_background.);
		//sound_bee.setLooping(id,true);








		//Shaperenderer oluşturulan circle'ı görmemizi sağlıyor bu sayede  çarpışmayı daha doğru yapabiliyoruz..

		//shapeRenderer=new ShapeRenderer();

		//Circles'larımızdan nesne yaratıyoruz..
		birdCircle=new Circle();
		beeCircles=new Circle[numberOf_bee];
		beeCircles2=new Circle[numberOf_bee];
		beeCircles3=new Circle[numberOf_bee];


		greenCircle=new Circle[numberOf_bee];





		//Kuşumuzu ekranın belli bir bölgesinde konumlandırdık.
		bird_x=Gdx.graphics.getWidth()/4;
		bird_y=Gdx.graphics.getHeight()/3;



		// Arılarımızın arasındaki mesafe ekranın/3  kadar fark olsun diye oluşturduk ard arda gelmesin diye..
		distance=Gdx.graphics.getWidth()/3;
		random=new Random();




		// for döngüsü ile Arılarımızın listelerine Y eksenini atatık ve  beeCircleramızıda for ile dönerek newledik..
		//Eğer newlemeseydik oluşturulan nesnelere atama yapamazdık..
		for (int i=0; i<numberOf_bee; i++){

			beeOffset[i]=(random.nextFloat())*(Gdx.graphics.getHeight());
			beeOffset2[i]=(random.nextFloat())*(Gdx.graphics.getHeight());
			beeOffset3[i]=(random.nextFloat())*(Gdx.graphics.getHeight());

			greenOffset[i]=(random.nextFloat()*(Gdx.graphics.getHeight()));



			bee_X[i]=Gdx.graphics.getWidth()-bee.getWidth()/2+i*distance;
			green_X[i]=Gdx.graphics.getWidth()-Green.getWidth()/3+i*distance;


			beeCircles[i]=new Circle();
			beeCircles2[i]=new Circle();
			beeCircles3[i]=new Circle();
			greenCircle[i]=new Circle();
		}






	}

	//float Bird_Genislik=Gdx.graphics.getWidth()/12;
	//float Bird_Yukseklık=Gdx.graphics.getHeight()/10;
	//Oyun devam ederken sadece burası çalışır ve saniyede 60 kere o yüzden oyun ile ilgili işlemerimizi burada yapıyoruz...
	@Override
	public void render () {

		//float Bird_Genislik=Gdx.graphics.getWidth()/12;
		//float Bird_Yukseklık=Gdx.graphics.getHeight()/10;
		//batch.begin ve batch.end arasında kodlarımızı yazıyoruz..
		//JustTouched kullanıcı tıklandığında ne olacağını bize söylememizi ister.
		batch.begin();

		//Burada ArkaPlanı çizdik..
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // Ekran ne kadarsa okadar genişlik ve yükselik ver


		if (gamestate == 1) {



			// Kuşun x pozisyonu eğer Arının "X" pozisyounundan büyükse Eğer Kuşumuz Arıya Dokunmadan geçti demektir..
			if (bird_x >= bee_X[score_bee]) {
				sound_coin.play(4f);

				score++;
				score_bee++;
				if (score % 5 == 0) {
					bee_velocity++;
				}
				if (score == 10) {
					background = new Texture("background2.png"); // buraya objemizi veriyoruz..

				}
				if (score == 20) {
					background = new Texture("background3.png"); // buraya objemizi veriyoruz..
				}
				if (score_bee == 3) {
					score_bee = 0;
				}
			}
			if (Yesil_durum){

				Bird_Genislik=Gdx.graphics.getWidth()/24;
				Bird_Yukseklık=Gdx.graphics.getHeight()/24;

				batch.draw(bird2, bird_x, bird_y, Bird_Genislik, Bird_Yukseklık);

				//Kuşumuza Bir circle verdik..
				birdCircle.set(bird_x + Bird_Genislik/2, bird_y +Bird_Yukseklık/2, Bird_Yukseklık/2);

				if (bird_x>=bee_X[score_bee2]){
					yesil_skor++;
					score_bee++;
					if (score_bee2==3){
						score_bee2=0;
					}
					if (yesil_skor==5){
						Yesil_durum=false;
						yesil_skor=0;
					}

				}
			}





				//Kullanıcı her tıkladığında velocity'e -8 değerini atıyoruz.. ?
				if (Gdx.input.justTouched()) {
					velocity = -8; //Burada'da y ekseni üzerinde zıplamanın hızını belirliyoruz.

				}


				//for döngüsü ile Arılarımızın Y ekseni üzerinde hangi konumda olacağını belirliyoruz..
				for (int i = 0; i < numberOf_bee; i++) {

					if (bee_X[i] < Gdx.graphics.getWidth() / 15) {
						bee_X[i] += numberOf_bee * distance;
						green_X[i]+=numberOf_bee*distance;
						beeOffset[i] = (random.nextFloat() + 0.1f) * (Gdx.graphics.getHeight() / 2 + 200);
						beeOffset2[i] = (random.nextFloat() + 0.2f) * (Gdx.graphics.getHeight() / 2 + 200);
						beeOffset3[i] = (random.nextFloat() + 0.3f) * (Gdx.graphics.getHeight() / 2 + 200);
						greenOffset[i]=(random.nextFloat()+0.3f)*(Gdx.graphics.getHeight()/2+200);


					} else {
						bee_X[i] -= bee_velocity;
						green_X[i]-=yem_velocity;
					}

					batch.draw(bee, bee_X[i], beeOffset[i],
							Gdx.graphics.getWidth() / 12, Gdx.graphics.getHeight() / 10);
					batch.draw(bee, bee_X[i], beeOffset2[i],
							Gdx.graphics.getWidth() / 12, Gdx.graphics.getHeight() / 10);
					batch.draw(bee, bee_X[i], beeOffset3[i],
							Gdx.graphics.getWidth() / 12, Gdx.graphics.getHeight() / 10);

					batch.draw(Green,green_X[i],greenOffset[i],Gdx.graphics.getWidth()/24,Gdx.graphics.getHeight()/20);

					//Burada Circlelarımızın boyutunu Arılarımızı Tam Kapsayacak Şekilde Olması için Düzenlemeleri yapıyoruz..
					beeCircles[i] = new Circle(bee_X[i] + Gdx.graphics.getWidth() / 24,
							Gdx.graphics.getHeight() / 20 + beeOffset[i], Gdx.graphics.getHeight() / 20);
					beeCircles2[i] = new Circle(bee_X[i] + Gdx.graphics.getWidth() / 24,
							Gdx.graphics.getHeight() / 20 + beeOffset2[i], Gdx.graphics.getHeight() / 20);
					beeCircles3[i] = new Circle(bee_X[i] + Gdx.graphics.getWidth() / 24,
							Gdx.graphics.getHeight() / 20 + beeOffset3[i], Gdx.graphics.getHeight() / 20);

					greenCircle[i]=new Circle(green_X[i]+Gdx.graphics.getWidth()/48,Gdx.graphics.getHeight()/40+greenOffset[i],Gdx.graphics.getHeight()/40);


				}



				//kuşun ekranın en üstüne çıkmaması için ...
				if (bird_y > 0) {
					//Burada Y ekseninde hareket ettiriyoruz..

					if (Gdx.graphics.getHeight() - 50 <= bird_y) {

						if (velocity < 0)
							velocity *= -1;
						velocity += gravity; //burada yer çekimi ile hızlanmayı topluyoruz..
						bird_y -= velocity;   //kuşumuz pozitif y ekseninde olduğu için kuşumuzun aşağıya doğru hareket etmesini sağlıyoruz çıkartarak..

					} else {
						velocity += gravity;  // yine yer çekimini ekliyoruz.
						bird_y = bird_y - velocity;  // burada yukarıdaki gibi velocity'i - ile çarpmadığımız için ve bird_y ile topladığımız için pozitif yönde yükselecek..
					}
				} else {
					gamestate = 2;
				}
			}



		else if (gamestate == 0) {
				//Oyun biterse ve kullanıcı yeniden ekrana tıklarsa oyunu baştan başlatıyoruz
				if (Gdx.input.justTouched()) {



					long id2=sound_background.play(4f);
					sound_background.setLooping(id2,true);
					gamestate = 1;
				}

			} else if (gamestate == 2) {

				background = new Texture("background.png"); // buraya objemizi veriyoruz..
				font_GameOver.draw(batch, "Game Over! Tap To Play Again ! ", 100, Gdx.graphics.getHeight() / 2);
				font_GameOver_Score.draw(batch, String.valueOf("Score: " + score), Gdx.graphics.getWidth() / 1.5f, Gdx.graphics.getHeight() / 2);


				Bird_Genislik=Gdx.graphics.getWidth()/12;
				Bird_Yukseklık=Gdx.graphics.getHeight()/10;





			//Oyun bitti eğer tekrardan başlarsa Create() Metodu çalışmayacağı için Kuşun  ve Arının Yerlerini Bidaha Atıyoruz..
				if (Gdx.input.justTouched()) {
					gamestate = 1;
					bird_y = Gdx.graphics.getHeight() / 3;
					for (int i = 0; i < numberOf_bee; i++) {

						beeOffset[i] = (random.nextFloat()) * (Gdx.graphics.getHeight());
						beeOffset2[i] = (random.nextFloat()) * (Gdx.graphics.getHeight());
						beeOffset3[i] = (random.nextFloat()) * (Gdx.graphics.getHeight());
						greenOffset[i]=(random.nextFloat()*(Gdx.graphics.getHeight()));


						bee_X[i] = Gdx.graphics.getWidth() - bee.getWidth() / 2 + i * distance;
						green_X[i]=Gdx.graphics.getWidth()-Green.getWidth()/3+i*distance;


						beeCircles[i] = new Circle();
						beeCircles2[i] = new Circle();
						beeCircles3[i] = new Circle();
						greenCircle[i]=new Circle();
					}
					velocity = 0;
					score_bee = 0;
					score = 0;
					bee_velocity = 3;
					yem_velocity=3;

				}

			}

		if (Yesil_durum==false){
			//Kuşumuzu çizdik..
			batch.draw(bird, bird_x, bird_y, Bird_Genislik, Bird_Yukseklık);

		}
		else{
			bird.dispose();
			//Kuşumuzu çizdik..
			batch.draw(bird2, bird_x, bird_y, Bird_Genislik, Bird_Yukseklık);

		}


		//Kuşumuza Bir circle verdik..
		birdCircle.set(bird_x + Bird_Genislik/2, bird_y +Bird_Yukseklık/2, Bird_Yukseklık/2);


		//Ekranda gösterilecek fontu yaptık...
			font_score.draw(batch, String.valueOf("Score: " + score), 70, Gdx.graphics.getHeight() - 40);


			//shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
			//shapeRenderer.setColor(com.badlogic.gdx.graphics.Color.BLACK);
			//shapeRenderer.circle(birdCircle.x,birdCircle.y,birdCircle.radius);


			for (int i = 0; i < numberOf_bee; i++) {


				//shapeRenderer.circle(beeCircles[i].x,beeCircles[i].y,beeCircles[i].radius);
				//shapeRenderer.circle(beeCircles2[i].x,beeCircles2[i].y,beeCircles2[i].radius);
				//shapeRenderer.circle(beeCircles3[i].x,beeCircles3[i].y,beeCircles3[i].radius);

				if (Intersector.overlaps(birdCircle, beeCircles[i]) || Intersector.overlaps(birdCircle, beeCircles2[i]) || Intersector.overlaps(birdCircle, beeCircles3[i])) {
					gamestate = 2;


				}
				if (Intersector.overlaps(birdCircle,greenCircle[i])){

					Bird_Genislik=Gdx.graphics.getWidth()/24;
					Bird_Yukseklık=Gdx.graphics.getHeight()/20;
					Yesil_durum=true;



				}


			}
			batch.end();
			//shapeRenderer.end();



	}

	@Override
	public void dispose () {

		sound_bee.dispose();
		sound_coin.dispose();
		sound_background.dispose();
		bird.dispose();
	}
}
