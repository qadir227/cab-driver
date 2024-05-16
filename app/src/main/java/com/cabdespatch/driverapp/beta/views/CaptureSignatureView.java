package com.cabdespatch.driverapp.beta.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pleng on 04/03/2015.
 */
public class CaptureSignatureView extends View
{
    private       Bitmap        _Bitmap;
    private       Canvas        _Canvas;
    private float _mX, _mY;
    private final float TouchTolerance = 4;
    private final Path          _Path;
    private final Paint         _BitmapPaint;
    private       Paint         _paint;
    public        SignatureData  _SignatureData;

    public enum SignatureState
    {
        Start,
        Move,
        End
    }
    public interface OnPointAddedListener
    {

    }



    public class SignatureData
    {

        private List<Point> currentPath;
        private List<List<Point>> _Paths;

        public List<List<Point>> getPaths() {return _Paths; }

        public  SignatureData()
        {
            this.Clear();
        }


        public Point LastPoint()
        {
            if (currentPath != null && currentPath.size() > 0)
            {
                return currentPath.get(currentPath.size() - 1);
            }
            return new Point(0, 0);
        }




        public void Clear()
        {
            _Paths = new ArrayList<List<Point>>();
            currentPath = new ArrayList<Point>();
        }




        public void AddPoint(SignatureState state, int x, int y)
        {
            if (state == SignatureState.Start)
            {
                currentPath = new ArrayList<Point>();
            }
            if (x != 0 && y != 0)
            {
                currentPath.add(new Point(x, y));
            }
            if (state == SignatureState.End)
            {
                if (currentPath != null)
                {
                    _Paths.add(currentPath);
                }
            }
            //OnPointAdded(null);
        }




        public int Length()
        {
            return _Paths.size();
        }



/*        protected void OnPointAdded(EventArgs e)
        {
            if (PointAdded != null)
            {
                PointAdded(this, e);
            }
        }*/


}

    public CaptureSignatureView(Context c, AttributeSet _attrs)
    {
        super(c, _attrs);


        if(_SignatureData==null)
        {
            _SignatureData = new SignatureData();
        }

        _Path = new Path();
        _BitmapPaint = new Paint(Paint.DITHER_FLAG);
        _paint = new Paint();

        _paint.setAntiAlias(true);
        _paint.setDither(true);
        _paint.setColor(Color.WHITE);
        _paint.setStyle(Paint.Style.STROKE);
        _paint.setStrokeJoin(Paint.Join.ROUND);
        _paint.setStrokeCap(Paint.Cap.ROUND);
        _paint.setStrokeWidth(8);


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);

        if((w>0) && (h>0))
        {
            _Bitmap = Bitmap.createBitmap(w, (h > 0 ? h : ((View) this.getParent()).getHeight()), Bitmap.Config.ARGB_8888);
            _Canvas = new Canvas(_Bitmap);
        }
    }



    @Override
    protected void onDraw(Canvas canvas)
    {
        canvas.drawColor(Color.BLACK);
        canvas.drawBitmap(_Bitmap, 0, 0, _BitmapPaint);
        canvas.drawPath(_Path, _paint);
    }

    private void TouchStart(float x, float y)
    {
        _Path.reset();
        _Path.moveTo(x, y);
        _mX = x;
        _mY = y;

        _SignatureData.AddPoint(SignatureState.Start, (int)x, (int)y);
    }

    private void TouchMove(float x, float y)
    {
        float dx = Math.abs(x - _mX);
        float dy = Math.abs(y - _mY);


        if (dx >= TouchTolerance || dy >= TouchTolerance)
        {
            _Path.quadTo(_mX, _mY, (x + _mX) / 2, (y + _mY) / 2);
            _SignatureData.AddPoint(SignatureState.Move, (int)x, (int)y);
            _mX = x;
            _mY = y;
        }
    }

    private void TouchUp()
    {
        if (!_Path.isEmpty())
        {
            _Path.lineTo(_mX, _mY);
            _Canvas.drawPath(_Path, _paint);
        }
        else
        {
            _Canvas.drawPoint(_mX, _mY, _paint);
        }
        _SignatureData.AddPoint(SignatureState.End, (int)_mX, (int)_mY);

        _Path.reset();
    }

@Override
    public boolean onTouchEvent(MotionEvent e)
    {
    float x = e.getX();
    float y = e.getY();

    switch (e.getAction())
    {
        case MotionEvent.ACTION_DOWN:
            TouchStart(x, y);
            this.invalidate();
            break;
        case MotionEvent.ACTION_MOVE:
            TouchMove(x, y);
            this.invalidate();
            break;
        case MotionEvent.ACTION_UP:
            TouchUp();
            this.invalidate();
            break;
    }
    return true;
}

    public void ClearCanvas()
    {
        _Canvas.drawColor(Color.BLACK);
        this.invalidate();
    }




    public Bitmap CanvasBitmap()
    {
        return _Bitmap;
    }


    public SignatureData getSignatureData()
    {
        return this._SignatureData;
    }

    public void Clear()
    {
        ClearCanvas();
        _SignatureData = new SignatureData();
    }

}
