package com.cabdespatch.driverapp.beta.dialogs;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cabdespatch.driverapp.beta.Globals;
import com.cabdespatch.driverapp.beta.HandyTools;
import com.cabdespatch.driverapp.beta.R;
import com.cabdespatch.driverapp.beta.STATUSMANAGER;
import com.cabdespatch.driverapp.beta.cabdespatchJob;
import com.cabdespatch.driverapp.beta.cdToast;

//this is a pseudo dialog... it doesn't extend android.dialog
public class Dialog_GetPrice extends PseudoDialog implements Globals.UnarmPanicListener
{


    TextView oLblPrompt;
    TextView oLblTotalFare;
    EditText oTxtInputFare;
    OnFareSetListener oListener;
    String oSurchargePrompt;
    Context context;


    public interface OnFareSetListener
    {
        void onFareSet(Double _fare);
    }

    public Dialog_GetPrice(Activity _a, ViewGroup _base, Boolean _applySurcharges)
    {
        super(_a,  _base, R.layout.dialog_price, R.id.dlgPrice_btnBack);

        this.context = _a;
        ImageButton btnPanic = (ImageButton) _base.findViewById(R.id.dlgPrice_btnPanic);
        Globals.CrossFunctions.armPanic(btnPanic, _a, this);
        btnPanic.setOnClickListener(this.btnPanic_Clicked());

        this.oTxtInputFare = (EditText) _base.findViewById(R.id.dlgPrice_txtFare);
        this.oTxtInputFare.setVisibility(View.VISIBLE);
        this.oTxtInputFare.setText(STATUSMANAGER.getCurrentJob(_a).getPrice());

        if(_applySurcharges)
        {
            this.oLblTotalFare = (TextView) _base.findViewById(R.id.dlgPrice_lblTotalFare);
            this.oLblTotalFare.setVisibility(View.VISIBLE);

            this.oSurchargePrompt = _a.getString(R.string.surcharge_prompt);

            this.oLblPrompt = (TextView) _base.findViewById(R.id.dlgPrice_lblSurchargePrompt);

            this.oTxtInputFare.addTextChangedListener(this.txtInputFare_TextWatcher());
        }

        ImageButton btnGo = (ImageButton) _base.findViewById(R.id.dlgPrice_btnGo);
        btnGo.setOnClickListener(btnGo_Clicked());
    }

    public void setOnFareSetListener(OnFareSetListener _listener)
    {
        this.oListener = _listener;
    }


    public View.OnClickListener btnGo_Clicked()
    {
        return new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                //send *PRE* surcharge price back to activity, as the activity
                //will be responsible for re-applying
                String fare = Dialog_GetPrice.this.oTxtInputFare.getText().toString();
                fare = fare.replace("£", "");

                final String farefinal = fare;

                if(fare.isEmpty())
                {
                    cdToast.showShort(v.getContext(), R.string.please_input_fare);
                }
                else
                {
                    Dialog_GetPrice.this.oTxtInputFare.clearFocus();
                    Dialog_GetPrice.this.oTxtInputFare.setFocusable(false);
                    Dialog_GetPrice.this.dismiss(new OnDissmissCompleteListener()
                    {
                        @Override
                        public void onDissmissComplete()
                        {
                            Dialog_GetPrice.this.oListener.onFareSet(Double.valueOf(farefinal));
                        }
                    });
                }


            }
        };
    }

    public View.OnClickListener btnPanic_Clicked()
    {
        return new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Globals.CrossFunctions.Panic(v.getContext());
            }
        };
    }

    public TextWatcher txtInputFare_TextWatcher()
    {
        return new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                try
                {
                    Double baseFare = Double.valueOf(s.toString());
                    Double surcharge = cabdespatchJob.calculateSurcharge(context, baseFare);

                    if(surcharge > 0)
                    {
                        oLblPrompt.setText(oSurchargePrompt.replace("$s", HandyTools.Strings.formatPrice(String.valueOf(surcharge))));
                        oLblPrompt.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        oLblPrompt.setVisibility(View.GONE);
                    }

                    oLblTotalFare.setText("£" + HandyTools.Strings.formatPrice(String.valueOf(baseFare + surcharge)));

                }
                catch(Exception ex)
                {

                }

            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        };
    }

    @Override
    public void unarmPanic()
    {
        //do nothing
    }

    /*
	public interface OnPriceGivenListener
	{
		void OnPriceGiven(String _price);
	}
	
	private OnPriceGivenListener oListener;
	
	public Dialog_GetPrice(Context context, OnPriceGivenListener _listener)
	{
		super(context);
		this.oListener = _listener;
		this.setCancelable(false);
		// TODO Auto-generated constructor stub
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.dialog_price);
		
		ImageButton btnGo = (ImageButton) this.findViewById(R.id.dlgPrice_btnGo);
		btnGo.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				EditText t = (EditText) Dialog_GetPrice.this.findViewById(R.id.dlgPrice_txtFare);
				String price = t.getText().toString();

                if(!(price.equals("")))
                {
                    Dialog_GetPrice.this.oListener.OnPriceGiven(price);
                    Dialog_GetPrice.this.dismiss();
                }
			}
		});
		
	}
	*/
	

}
