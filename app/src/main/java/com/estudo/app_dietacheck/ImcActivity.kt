package com.estudo.app_dietacheck

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.estudo.app_dietacheck.databinding.ActivityImcBinding
import java.text.DecimalFormat
import kotlin.math.round

class ImcActivity : AppCompatActivity() {

    private lateinit var binding: ActivityImcBinding

    private lateinit var editWeight: EditText
    private lateinit var editHeight: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityImcBinding.inflate(layoutInflater)

        setContentView(binding.root)

        ///------------------////

        editWeight = binding.inputEdtWeight
        editHeight = binding.inputEdtHeight

        val btnCalculate: Button = binding.btnImcGenerate
        val txtToolbar = binding.txtToolbar
        val txtResult = binding.txtResult

        txtToolbar.text = getString(R.string.titleIMC)

        btnCalculate.setOnClickListener {
            if (!validate()) {
                Toast.makeText(this, R.string.validate, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val weight = editWeight.text.toString().toInt()
            val height = editHeight.text.toString().toInt()

            val result = calculateImc(weight, height)

            val df = DecimalFormat("0.00")
            txtResult.text = df.format(result)

            if (result <= 18.5){
                binding.labelResult.text = "Seu IMC é: ${getString(R.string.magreza)}"
                txtResult.setTextColor(Color.YELLOW)
            }else if (result > 18.5 && result <= 24.9){
                binding.labelResult.text = "Seu IMC é: ${getString(R.string.normal)}"
                txtResult.setTextColor(Color.BLACK)
            }else if(result > 24.9 && result <= 29.99){
                binding.labelResult.text = "Seu IMC é: ${getString(R.string.sobrepeso)}"
                txtResult.setTextColor(Color.GRAY)
            }else if (result > 29.99 && result <= 39.99){
                binding.labelResult.text = "Seu IMC é: ${getString(R.string.obesidade)}"
                txtResult.setTextColor(Color.rgb(255,69,0))
            }else{
                binding.labelResult.text = "Seu IMC é: ${getString(R.string.obesidade_grave)}"
                txtResult.setTextColor(Color.RED)
            }

        }
    }

    private fun calculateImc(weight: Int, height: Int): Double {
        // peso / (altura * altura)
        return weight / ((height / 100.0) * (height / 100.0))
    }

    private fun validate(): Boolean {
        return (editWeight.text.toString().isNotEmpty()
                && editHeight.text.toString().isNotEmpty()
                && !editWeight.text.toString().startsWith("0")
                && !editHeight.text.toString().startsWith("0"))
    }
}
