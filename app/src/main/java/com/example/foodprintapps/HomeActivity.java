package com.example.foodprintapps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class HomeActivity extends AppCompatActivity {

    ImageButton out;
    TextView textView;
    RecyclerView recyclerView;
    ImageView imageView;
    FloatingActionButton floatingBtn;
    FirebaseAuth firebaseAuth;
    GoogleSignInClient googleSignInClient;
    FirebaseRecyclerOptions<FoodModel> foodModel;
    FirebaseRecyclerAdapter<FoodModel, FoodViewHolder> foodAdapter;
    DatabaseReference dataRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        imageView = findViewById(R.id.imageview);
        out = findViewById(R.id.image_back);
        textView = findViewById(R.id.name);
        dataRef = FirebaseDatabase.getInstance().getReference().child("Food");
        recyclerView = findViewById(R.id.recyclerView);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        floatingBtn = findViewById(R.id.floatingBtn);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);

        if (firebaseUser != null) {

            //image
            Glide.with(HomeActivity.this).load(firebaseUser.getPhotoUrl()).into(imageView);

            //Name
            textView.setText(firebaseUser.getDisplayName());
        }

        floatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), UploadActivity.class));
            }
        });

        LoadData();
    }

    private void LoadData() {
        foodModel = new FirebaseRecyclerOptions.Builder<FoodModel>().setQuery(dataRef, FoodModel.class).build();
        foodAdapter = new FirebaseRecyclerAdapter<FoodModel, FoodViewHolder>(foodModel) {
            @Override
            protected void onBindViewHolder(@NonNull FoodViewHolder holder, int position, @NonNull FoodModel model) {
                holder.textView.setText((model.getFoodName()));
                Picasso.get().load(model.getImageUrl()).into(holder.imageView);
            }

            @NonNull
            @Override
            public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_view, parent, false);
                return new FoodViewHolder(v);
            }
        };
        foodAdapter.startListening();
        recyclerView.setAdapter(foodAdapter);



        googleSignInClient = GoogleSignIn.getClient(HomeActivity.this, GoogleSignInOptions.DEFAULT_SIGN_IN);
        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            firebaseAuth.signOut();
                            Toast.makeText(getApplicationContext(), "Logout Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_food, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.change) {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);

        }else if (item.getItemId() == R.id.homeA) {
            Intent mIntent2 = new Intent(HomeActivity.this, HomeActivity.class);
            startActivity(mIntent2);

        }else if (item.getItemId() == R.id.about) {
            Intent mIntent1 = new Intent(HomeActivity.this, AboutActivity.class);
            startActivity(mIntent1);

        }

        return super.onOptionsItemSelected(item);
    }

}