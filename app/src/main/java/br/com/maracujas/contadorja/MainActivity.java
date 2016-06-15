package br.com.maracujas.contadorja;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import br.com.maracujas.contadorja.R;
import br.com.maracujas.contadorja.domain.User;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SubMenu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

//import com.firebase.client.DataSnapshot;
//import com.firebase.client.Firebase;
//import com.firebase.client.FirebaseError;
//import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
	private int pontuacao;
	private String nomeArquivo = "pontuacao.txt";
	//DatabaseReference mRef;
	private FirebaseAuth mAuth;
	private FirebaseAuth.AuthStateListener authStateListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//mRef = FirebaseDatabase.getInstance().getReference();

		if (savedInstanceState == null) {
			pontuacao = 0;
		} else {
			pontuacao = savedInstanceState.getInt("pontuacao");
		}
		final TextView resultadoTextView = (TextView) this
				.findViewById(R.id.resutadoTextView4);
		Button maisButton = (Button) this.findViewById(R.id.maisButton);
		Button menosButton = (Button) this.findViewById(R.id.menosButton);
		Button salvarButton = (Button) this.findViewById(R.id.salvarButton);
		Button buscarButton = (Button) this.findViewById(R.id.buscarButton);
		Button playerButton = (Button) this.findViewById(R.id.playerButton);
		Button mais1Button = (Button) this.findViewById(R.id.mais1bt);
		Button menos1Button = (Button) this.findViewById(R.id.menos1Bt);
		maisButton.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				pontuacao = pontuacao + 1;
				resultadoTextView.setText(Integer.toString(pontuacao));
				return false;
			}
		});
			
		
		menosButton.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				pontuacao = pontuacao - 1;
				resultadoTextView.setText(Integer.toString(pontuacao));
				return false;
			}
		});
		
		maisButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				pontuacao = pontuacao + 1;
				resultadoTextView.setText(Integer.toString(pontuacao));
				// String result = resultadoTextView.getText().toString();
				// int resultado = Integer.parseInt(result)+1;
				// resultadoTextView.setText(Integer.toString(resultado));
			}

		});
		mais1Button.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				pontuacao = pontuacao + 1;
				resultadoTextView.setText(Integer.toString(pontuacao));
				// String result = resultadoTextView.getText().toString();
				// int resultado = Integer.parseInt(result)+1;
				// resultadoTextView.setText(Integer.toString(resultado));
			}

		});
		menosButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				pontuacao = pontuacao - 1;
				resultadoTextView.setText(Integer.toString(pontuacao));
				// String result = resultadoTextView.getText().toString();
				// int resultado = Integer.parseInt(result)-1;
				// resultadoTextView.setText(Integer.toString(resultado));
			}

		});
		menos1Button.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				pontuacao = pontuacao - 1;
				resultadoTextView.setText(Integer.toString(pontuacao));
				// String result = resultadoTextView.getText().toString();
				// int resultado = Integer.parseInt(result)-1;
				// resultadoTextView.setText(Integer.toString(resultado));
			}

		});

		salvarButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// File arquivo = getFileStreamPath(nomeArquivo);
				/*
				 * if (arquivo.exists()){ boolean apagou =
				 * deleteFile(nomeArquivo); //Toast.makeText(MainActivity.this,
				 * apagou ? "Apagou." : "N�o apagou.",
				 * Toast.LENGTH_SHORT).show(); } else{
				 * //Toast.makeText(MainActivity.this,
				 * "Voc� n�o tem abordagens, n�o d� para apagar.",
				 * Toast.LENGTH_SHORT).show(); }
				 */


				AlertDialog.Builder confirmarSalvar = new AlertDialog.Builder(MainActivity.this);
				confirmarSalvar.setTitle("Salvar");
				confirmarSalvar.setMessage("Tem Certeza que deseja Salvar?");
				confirmarSalvar.setPositiveButton("Sim",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
							try {
									FileOutputStream fos = openFileOutput(
											nomeArquivo, Context.MODE_PRIVATE);
									String conteudo = resultadoTextView
											.getText().toString();

									// conteudo = conteudo + "\n";
									fos.write(conteudo.getBytes());
									fos.close();
								//mRef.setValue(conteudo);
								Toast.makeText(MainActivity.this, "Salvo Com Sucesso",Toast.LENGTH_SHORT).show();
								} catch (FileNotFoundException e) {
									Toast.makeText(MainActivity.this,
											"Arquivo não encontrado.",
											Toast.LENGTH_SHORT).show();
								} catch (IOException e) {
									Toast.makeText(MainActivity.this,
											"Exceção de entrada/saída.",
											Toast.LENGTH_SHORT).show();
								}

							}
						});
				confirmarSalvar.setNegativeButton("não",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.cancel();
							}
						});
				confirmarSalvar.show();

			}
		});
		

		buscarButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				File arquivo = getFileStreamPath(nomeArquivo);
				if (arquivo.exists()) {
					try {
						FileInputStream fis = openFileInput(nomeArquivo);
						int tamanho = fis.available();
						byte[] bytes = new byte[tamanho];
						fis.read(bytes);
						fis.close();
						String conteudo = new String(bytes);
						resultadoTextView.setText(conteudo);
						pontuacao = Integer.parseInt(conteudo);
					} catch (IOException e) {
						Toast.makeText(MainActivity.this,
								"Exceção de acesso ao arquivo.",
								Toast.LENGTH_SHORT).show();
					}

				} else {
					Toast.makeText(MainActivity.this, "O arquivo não existe.",
							Toast.LENGTH_SHORT).show();
				}

			}
		});

		playerButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, PlayerActivity.class);
				startActivity(i);

			}

		});

		authStateListener = new FirebaseAuth.AuthStateListener() {
			@Override
			public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

				if( firebaseAuth.getCurrentUser() == null  ){
					Intent intent = new Intent( MainActivity.this, LoginActivity.class );
					startActivity( intent );
					finish();
				}
			}
		};

		mAuth = FirebaseAuth.getInstance();
		mAuth.addAuthStateListener( authStateListener );

	}
	
	
	/*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.menus_java, menu);
        //return true;
    	boolean result = super.onCreateOptionsMenu(menu);
    	super.onCreateOptionsMenu(menu);
    	menu.add(0,1, 0,	"Configurações").setIcon(R.drawable.ic_launcher);
    	menu.add(0, 2, 0, "Ranking");
    	menu.add(0, 3, 0, "Sobre");
    	
    	SubMenu utilitario = menu.addSubMenu("Utilitarios");
    	utilitario.add(0, 3, 0, "Pesquisa");
    	utilitario.add(0, 4, 0, "Editar");
    	
    	return result;
    }*/



	@Override
	protected void onStart() {
		super.onStart();	}
		//mRef = new Firebase("https://contadorja.firebaseio.com/Pontos");
		//DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://contadorja.firebaseio.com/Pontos");
		//DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
		/*mRef.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				String numero = dataSnapshot.getValue(String.class);

				try {
					FileOutputStream fos = openFileOutput(nomeArquivo, Context.MODE_PRIVATE);
					fos.write(numero.getBytes());
					fos.close();
				} catch (FileNotFoundException e) {
					Toast.makeText(MainActivity.this,
							"Arquivo não encontrado.",
							Toast.LENGTH_SHORT).show();
				} catch (IOException e) {
					Toast.makeText(MainActivity.this,
							"Exceção de entrada/saída.",
							Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void onCancelled(DatabaseError databaseError) {

			}
		});*/

		/*mRef.child("Pontos").addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				String numero = dataSnapshot.getValue(String.class);




				try {
					FileOutputStream fos = openFileOutput(nomeArquivo, Context.MODE_PRIVATE);
					fos.write(numero.getBytes());
					fos.close();
				} catch (FileNotFoundException e) {
					Toast.makeText(MainActivity.this,
							"Arquivo não encontrado.",
							Toast.LENGTH_SHORT).show();
				} catch (IOException e) {
					Toast.makeText(MainActivity.this,
							"Exceção de entrada/saída.",
							Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void onCancelled(DatabaseError databaseError) {

			}
		});*/

	@Override
	protected void onDestroy() {
		super.onDestroy();
		//adapter.cleanup();

		if( authStateListener != null ){
			mAuth.removeAuthStateListener( authStateListener );
		}
	}
	// MENU
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		User user = new User();

		if( user.isSocialNetworkLogged( this ) ){
			getMenuInflater().inflate(R.menu.menu_social_network_logged, menu);
		}
		else{
			getMenuInflater().inflate(R.menu.menu, menu);
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		if(id == R.id.action_update){
			startActivity(new Intent(this, UpdateActivity.class));
		}
		else if(id == R.id.action_update_login){
			startActivity(new Intent(this, UpdateLoginActivity.class));
		}
		else if(id == R.id.action_update_password){
			startActivity(new Intent(this, UpdatePasswordActivity.class));
		}
		/*else if(id == R.id.action_link_accounts){
			startActivity(new Intent(this, LinkAccountsActivity.class));
		}*/
		else if(id == R.id.action_remove_user){
			startActivity(new Intent(this, RemoveUserActivity.class));
		}
		else if(id == R.id.action_logout){
			FirebaseAuth.getInstance().signOut();
			finish();
		}

		return super.onOptionsItemSelected(item);
	}/*
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch(item.getItemId()){
    	case 1: mensagemExibir("Configurações","Chamara a tela de configurações");break;
    	case 2: mensagemExibir("Ranking","Chamará a tela de Raning");break;
    	case 3: mensagemExibir("Sobre","Chamará a tela de Sobre");break;


    	}
    	return super.onOptionsItemSelected(item);
    }
    public void mensagemExibir(String titulo, String texto) {
    	AlertDialog.Builder mensagem = new AlertDialog.Builder(MainActivity.this);
    	mensagem.setTitle(titulo);
    	mensagem.setMessage(texto);
    	mensagem.setNeutralButton("OK", null);
    	mensagem.show();
    }*/
}
    
