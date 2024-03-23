package com.estudo.app_dietacheck

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.estudo.app_dietacheck.databinding.ActivityImcBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder


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

        txtToolbar.text = getString(R.string.titleIMC)

        btnCalculate.setOnClickListener {
            if (!validate()) {
                Toast.makeText(this, R.string.validate, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val weight = editWeight.text.toString().toInt()
            val height = editHeight.text.toString().toInt()

            val result = calculateImc(weight, height)
            val classification = getString(classification(result))

            MaterialAlertDialogBuilder(
                this,
                androidx.appcompat.R.style.Animation_AppCompat_Dialog
            )
                .setTitle(R.string.txt_result)
                .setMessage(getString(R.string.messageIMC, result, classification))
                .setIcon(R.drawable.baseline_medical_services_24_red)
                .setPositiveButton(android.R.string.ok, null)
                .create()
                .show()
        }
    }
    @StringRes
    private fun classification(result: Double): Int {
        return when {
            result <= 18.5 -> R.string.magreza
            result <= 24.9 -> R.string.normal
            result <= 29.99 -> R.string.sobrepeso
            result <= 39.99 -> R.string.obesidade
            else -> R.string.obesidade_grave
        }
    }
    private fun calculateImc(weight: Int, height: Int): Double {
        return weight / ((height / 100.0) * (height / 100.0))
    }
    private fun validate(): Boolean {
        return (editWeight.text.toString().isNotEmpty()
                && editHeight.text.toString().isNotEmpty()
                && !editWeight.text.toString().startsWith("0")
                && !editHeight.text.toString().startsWith("0"))
    }
}
