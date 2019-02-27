package com.xintai.xhao;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.xintai.xhao.bean.Adverindex;
import com.xintai.xhao.bean.CakeBean;
import com.xintai.xhao.dagger.DaggerSimpleComponent;
import com.xintai.xhao.dagger.Student;
import com.xintai.xhao.network.RequestApiDemo;
import com.xintai.xhao.utils.FileUtils;

import java.io.File;

import javax.inject.Inject;

import rx.Observer;

public class MainActivity extends AppCompatActivity {

    private TextView mTv, mTv2, mTv3;
    ImageView imageView1, imageView2, imageView3;
    Button mbtn;
    //添加@Inject注解，表示是需要注入的，（就是编译时候生成的实例）；
    //一般情况下，不同的Fragment和Activity里面，里面注入的实例是不一样的；
    @Inject
    Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_act);

        mTv = (TextView) findViewById(R.id.main_tv);
        mTv2 = (TextView) findViewById(R.id.main_tv2);
        mTv3 = (TextView) findViewById(R.id.main_tv3);
        imageView1 = (ImageView) findViewById(R.id.main_img1);
        imageView2 = (ImageView) findViewById(R.id.main_img2);
        imageView3 = (ImageView) findViewById(R.id.main_img3);
        mbtn = (Button) findViewById(R.id.btn1);

        //        NetWorkUtils.isNetworkConnected(this);

        // 将Dagger2在编译时候生成的实例注入到请求注入的Activity和Fragment;
        DaggerSimpleComponent.builder()
                //                .simpleModule(new SimpleModule(this))
                .build()
                .inject(this);

        mbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.i("xhao", "点击了button" + student.getName() + "\n" + student);
//                //                Toast.makeText(MainActivity.this, student.getName(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, TestAct.class);
                startActivity(intent);

//                boolean b = F.clearCache();
//                if(b){
//                    Toast.makeText(MainActivity.this,"删除缓存成功",Toast.LENGTH_SHORT).show();
//                }
            }
        });

        RequestApiDemo.AdvGet("259579", new Observer<Adverindex>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(getApplication(),e.toString(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNext(Adverindex adverindex) {

                int lens = adverindex.getAdverup().size();
                StringBuffer buffer = new StringBuffer();
                for (int i = 0; i < lens; i++) {
                    buffer.append(adverindex.getAdverup().get(i).getPtitle());
                    buffer.append("\n");
                }
                mTv.setText(buffer.toString());
            }
        });

        RequestApiDemo.getCakeCodeGet(new Observer<CakeBean>() {
                                    @Override
                                    public void onCompleted() {

                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        mTv.setText(e.getLocalizedMessage());
                                        //                                        mTv2.setText(e.getMessage());
                                    }

                                    @Override
                                    public void onNext(CakeBean cakeBean) {
                                        int lens = cakeBean.getData().getList().size();
                                        StringBuffer buffer = new StringBuffer();
                                        for (int i = 0; i < lens; i++) {
                                            buffer.append(cakeBean.getData().getList().get(i).getTitle());
                                            buffer.append("\n");
                                        }
                                        mTv2.setText(buffer.toString());

//                                       Glide的关联配置在AndroidMainfest里面
                                        Glide.with(MainActivity.this)
                                                .load(cakeBean.getData().getList().get(0).getThumb())
                                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                                .placeholder(R.mipmap.ic_launcher)
                                                .thumbnail(1f)
                                                .error(R.mipmap.ic_launcher)
                                                .into(imageView1);

                                        Glide.with(MainActivity.this)
                                                .load(cakeBean.getData().getList().get(1).getThumb())
                                                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                                .placeholder(R.mipmap.ic_launcher)
                                                .thumbnail(1f)
                                                .error(R.mipmap.ic_launcher)
                                                .into(imageView2);

                                        Glide.with(MainActivity.this)
                                                .load(cakeBean.getData().getList().get(2).getThumb())
                                                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                                .placeholder(R.mipmap.ic_launcher)
                                                .thumbnail(1f)
                                                .error(R.mipmap.ic_launcher)
                                                .into(imageView3);

                                        //                                        mTv.setText(cakeBean.getData().getList().get(0).getTitle());
                                    }
                                }
        );


        long lens = FileUtils.getFolderSize(new File(F.glade_cache_path));
        mTv3.setText(FileUtils.getFormatSize((double) lens));

        //        RequestApi.AdvGetPic("getValidatecode", new Observer<okhttp3.ResponseBody>() {
        //            @Override
        //            public void onCompleted() {
        //
        //            }
        //
        //            @Override
        //            public void onError(Throwable e) {
        //
        //            }
        //
        //            @Override
        //            public void onNext(okhttp3.ResponseBody response) {
        ////                mTv2.setText(response.toString());
        //                    InputStream is = response.byteStream();
        //                    Bitmap bm = BitmapFactory.decodeStream(is);
        //                    imageView1.setImageBitmap(bm);
        //            }
        //        });
    }
}
