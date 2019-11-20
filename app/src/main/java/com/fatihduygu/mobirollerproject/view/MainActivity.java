package com.fatihduygu.mobirollerproject.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.fatihduygu.mobirollerproject.R;

public class MainActivity extends AppCompatActivity {
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setup up button on application
        navController= Navigation.findNavController(this,R.id.fragment);
        NavigationUI.setupActionBarWithNavController(this,navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        //used for adding "up button"
        return NavigationUI.navigateUp(navController, (DrawerLayout) null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (navController.getCurrentDestination().getId()==R.id.productFragment){
            MenuInflater menuInflater=getMenuInflater();
            menuInflater.inflate(R.menu.product_menu,menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sortByName:
                ProductFragment.productFragmentViewModel.sortByName();
                break;
            case R.id.sortByCategory:
                ProductFragment.productFragmentViewModel.sortByCategory();
                break;

            case R.id.sortByPrice:
                ProductFragment.productFragmentViewModel.sortByPrice();
                break;
        }
        
        return super.onOptionsItemSelected(item);
    }
}
