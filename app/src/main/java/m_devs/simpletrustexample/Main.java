package m_devs.simpletrustexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import m_devs.simpletrust.SimpleTrust;

public class Main extends AppCompatActivity {

    /**
     * Create the <code>SimpleTrust</code> object in the class where you want to use it.
     * Or create it in the <code>Application</code> class.
     *
     * @param savedInstanceState the savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SimpleTrust simpleTrust = new SimpleTrust();
        simpleTrust.addTrusted("your-trusted-domain.com");
        simpleTrust.load();

        //simpleTrust.reset(); reset the your trust manager

    }

}
