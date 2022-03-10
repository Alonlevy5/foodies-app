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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.comas.foodies.model.Model;
import com.comas.foodies.model.Recipe;

import org.jetbrains.annotations.NotNull;

import java.io.FileNotFoundException;
import java.io.InputStream;


public class AddRecipeFragment extends Fragment {


    ImageView avatarImv;
    EditText nameEt;
    EditText idEt;
    EditText descEt;
    Button saveBtn;
    Button cancelBtn;
    ProgressBar progressBar;

    ImageButton cameraBtn;
    ImageButton galleryBtn;
    ImageView AvatarImv;

//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    public AddRecipeFragment() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment RecipeCreateFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static AddRecipeFragment newInstance(String param1, String param2) {
//
//        AddRecipeFragment fragment = new AddRecipeFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_recipe, container, false);

        avatarImv = view.findViewById(R.id.addRecipe_avatar_img);
        nameEt = view.findViewById(R.id.addRecipe_name_et);
        descEt = view.findViewById(R.id.addRecipe_desc_et);
        idEt = view.findViewById(R.id.addRecipe_id_et);

        //camera part
        cameraBtn = view.findViewById(R.id.addRecipe_camera_imgBtn);
        galleryBtn = view.findViewById(R.id.addRecipe_gallery_imgBtn);
        avatarImv = view.findViewById(R.id.addRecipe_avatar_img);

        //buttons handling
        saveBtn = view.findViewById(R.id.addRecipe_save_btn);
        cancelBtn = view.findViewById(R.id.addRecipe_cancel_btn);

        progressBar = view.findViewById(R.id.main_progressbar);
        progressBar.setVisibility(View.GONE);

        saveBtn.setOnClickListener(v -> save());

        cancelBtn.setOnClickListener(v -> Navigation.findNavController(v).navigateUp());


        cameraBtn.setOnClickListener(v -> openCamera());


        galleryBtn.setOnClickListener(v -> openGallery());

        return view;
    }


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

    private void save() {

        progressBar.setVisibility(View.VISIBLE);
        saveBtn.setEnabled(false);
        cancelBtn.setEnabled(false);

        String desc = descEt.getText().toString();
        String name = nameEt.getText().toString();
        String id = idEt.getText().toString();

        Log.d("TAG", "saved name:" + name + " id:" + id);

        Recipe recipe = new Recipe(name, id, desc);

        // Save Image url to recipe
        if (imageBitmap != null) {
            Model.instance.saveImage(imageBitmap, id + ".jpg", url -> {
                recipe.setImageUrl(url);
                Model.instance.addRecipe(recipe, () -> {
                    Navigation.findNavController(nameEt).navigateUp();
                });
            });
        } else {

            Model.instance.addRecipe(recipe, () -> Navigation.findNavController(nameEt).navigateUp());
        }
    }
}

