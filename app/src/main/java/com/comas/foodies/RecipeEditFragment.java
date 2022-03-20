package com.comas.foodies;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.comas.foodies.model.Model;
import com.comas.foodies.model.Recipe;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.InputStream;


public class RecipeEditFragment extends Fragment {


    TextView nameTv;
    TextView descEt;
    Button saveBtn;
    ImageView avatarImv;


    ImageButton editBtn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recipe_edit, container, false);

        String recipeId = RecipeDetailsFragmentArgs.fromBundle(getArguments()).getRecipeId();

        Model.instance.getRecipeById(recipeId, new Model.GetRecipesById() {
            @Override
            public void onComplete(Recipe recipe) {
                nameTv.setText(recipe.getName());
                if (recipe.getImageUrl() != null) {
                    Picasso.get()
                            .load(recipe.getImageUrl())
                            .into(avatarImv);
                }
                descEt.setText(recipe.getDesc());
            }
        });

        nameTv = view.findViewById(R.id.edit_recipe_namET);
        descEt = view.findViewById(R.id.edit_recipe_descET);
        saveBtn = view.findViewById(R.id.edit_recipe_saveBtn);
        avatarImv = view.findViewById(R.id.edit_image_imgv);
        editBtn = view.findViewById(R.id.edit_recipe_edit_imgBtn);


        //send the field we want to update and then return to detail page
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Model.instance.updateRecipeById(recipeId, nameTv.getText().toString(), descEt.getText().toString(), new Model.UpdateRecipeById() {
                    @Override
                    public void onComplete(Recipe recipe) {
                        nameTv.setText(recipe.getName());
                        descEt.setText(recipe.getDesc());
                        //***

                        // Save Image url to recipe
                        if (imageBitmap != null) {
                            Model.instance.saveImage(imageBitmap, recipe.getId() + ".jpg", url -> {
                                recipe.setImageUrl(url);
                                Model.instance.addRecipe(recipe, () -> {
                                    Navigation.findNavController(nameTv).navigateUp();
                                });
                            });
                        } else {

                            Model.instance.addRecipe(recipe, () -> Navigation.findNavController(nameTv).navigateUp());
                        }
                    }
                });

            }

        });


        editBtn.setOnClickListener(v -> openCamera());

        return view;
    }


    /***
     *** Photo Part
     ***/

    private final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;

    private void openGallery() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, REQUEST_IMAGE_PICK);
    }

    private void openCamera() {
        Intent takePicIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePicIntent, REQUEST_IMAGE_CAPTURE);
    }

    Bitmap imageBitmap;

    @Override
    // after openCamera() this function executes when user either takes a picture by camera or cancel action.
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                imageBitmap = (Bitmap) extras.get("data");
                avatarImv.setImageBitmap(imageBitmap);

            }
        } else if (requestCode == REQUEST_IMAGE_PICK) {
            if (resultCode == RESULT_OK) {
                try {
                    final Uri imageUri = data.getData();
                    final InputStream imageStream = getContext().getContentResolver().openInputStream(imageUri);
                    imageBitmap = BitmapFactory.decodeStream(imageStream);
                    avatarImv.setImageBitmap(imageBitmap);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                }

            }
        }
    }


}