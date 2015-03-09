package org.wildstang.wildrank.androidv2.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import org.wildstang.wildrank.androidv2.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Liam on 1/24/2015.
 */
public class WhiteboardView extends View
{
    Bitmap field;
    Bitmap binMagnet;
    Bitmap toteMagnet;
    Bitmap coopMagnet;
    Bitmap litterMagnet;
    Bitmap robotMagnet;
    Bitmap bin;
    Bitmap tote;
    Bitmap coop;
    Bitmap litter;
    Bitmap robot;

    boolean run = false;
    boolean penOn = false;
    boolean magnetheld = false;

    double scale;
    int currentmagnet = 0;
    int xOffset;
    int yOffset;

    List<Magnet> magnets = new ArrayList<>();
    List<Button> buttons = new ArrayList<>();
    List<List<Point>> points = new ArrayList<>();

    //this is a constructor, you better know what that is
    public WhiteboardView(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        //loads all the raw images
        field = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.field);
        bin = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.greenbin);
        tote = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.graytote);
        coop = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.yellowtote);
        litter = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.noodle);
        robot = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.dozer);
    }

    //this is run when the view is created
    //it initializes images and buttons
    public void init()
    {
        //loads all the magnet images that can be manipulated
        scale = ((double)getHeight()/(double)field.getHeight());
        field = Bitmap.createScaledBitmap(field,(int) (scale * field.getWidth()), getHeight(), false);
        binMagnet = Bitmap.createScaledBitmap(bin,(int)(scale * bin.getWidth()), (int)(scale * bin.getHeight()), false);
        toteMagnet = Bitmap.createScaledBitmap(tote, (int)(scale * tote.getWidth()), (int)(scale * tote.getHeight()), false);
        coopMagnet = Bitmap.createScaledBitmap(coop,  (int)(scale * coop.getWidth()), (int)(scale * coop.getHeight()), false);
        litterMagnet = Bitmap.createScaledBitmap(litter, (int) (scale * litter.getWidth()), (int)(scale * litter.getHeight()), false);
        robotMagnet = Bitmap.createScaledBitmap(robot, 2*coopMagnet.getWidth(), 2*(int)(robot.getHeight() * ((double)coopMagnet.getWidth() / (double)robot.getWidth())), false);

        //loads all the button images
        scale = (((double)getHeight()/8.0)/(double)tote.getWidth());
        bin = Bitmap.createScaledBitmap(bin,(int)(scale * bin.getWidth()), (int)(scale * bin.getHeight()), false);
        tote = Bitmap.createScaledBitmap(tote, (int)(scale * tote.getWidth()), (int)(scale * tote.getHeight()), false);
        coop = Bitmap.createScaledBitmap(coop,  (int)(scale * coop.getWidth()), (int)(scale * coop.getHeight()), false);
        litter = Bitmap.createScaledBitmap(litter, (int) (scale * litter.getWidth()), (int)(scale * litter.getHeight()), false);
        robot = Bitmap.createScaledBitmap(robot, coop.getWidth(), (int)(robot.getHeight() * ((double)coop.getWidth() / (double)robot.getWidth())), false);

        //creates the side buttons for clearing and using the pen
        int width = ((getWidth() / 6) + field.getWidth());
        int buttonWidth = getWidth() - (width + 20);
        buttons.add(new Button(width + 10, 10, buttonWidth, 2*buttonWidth/3 + 10, "Clear!", false));
        buttons.add(new Button(width + 10, 2*buttonWidth/3 + 30, buttonWidth, 2*buttonWidth/3 + 10, "Pen On/Off", true));

        //listener for touching the screen
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                //draws when touched
                invalidate();

                //positions where touched
                int x = (int) event.getX();
                int y = (int) event.getY();

                //switch to check whether the screen was pressed on, dragged on, or let go
                int action = event.getActionMasked();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        //if pressed on
                        if (!penOn && y >= 10 && y <= 10 + tote.getHeight() && x >= 10 && x <= tote.getWidth() + 10)
                        {
                            //if the grey tote button is pressed on add a new one
                            currentmagnet = magnets.size();
                            magnets.add(new Magnet(x, y, toteMagnet));
                            magnetheld = true;
                        }
                        else if (!penOn && y >= 20 + tote.getHeight() && y <= 20 + tote.getHeight() + bin.getHeight() && x >= 10 && x <= bin.getWidth() + 10)
                        {
                            //if the green bin button is pressed on add a new one
                            currentmagnet = magnets.size();
                            magnets.add(new Magnet(x, y, binMagnet));
                            magnetheld = true;
                        }
                        else if (!penOn && y >= 30 + tote.getHeight() + bin.getHeight() && y <= 30 + tote.getHeight() + bin.getHeight() + coop.getHeight() && x >= 10 && x <= coop.getWidth() + 10)
                        {
                            //if the yellow tote button is pressed on add a new one
                             currentmagnet = magnets.size();
                             magnets.add(new Magnet(x, y, coopMagnet));
                             magnetheld = true;
                        }
                        else if (!penOn && y >= 40 + tote.getHeight() + bin.getHeight() + coop.getHeight() && y <= 40 + tote.getHeight() + bin.getHeight() + coop.getHeight() + litter.getHeight() && x >= 10 && x <= litter.getWidth()*10)
                        {
                            //if the litter button is pressed on add a new one (this has a buffer because it is hard to press)
                            currentmagnet = magnets.size();
                            magnets.add(new Magnet(x, y, litterMagnet));
                            magnetheld = true;
                        }
                        else if (!penOn && y >= 50 + tote.getHeight() + bin.getHeight() + coop.getHeight() && y <= 40 + tote.getHeight() + bin.getHeight() + coop.getHeight() + litter.getHeight() + robot.getHeight() && x >= 10 && x <= robot.getWidth() + 10)
                        {
                            //if the robot button is pressed on add a new one
                            currentmagnet = magnets.size();
                            magnets.add(new Magnet(x, y, robotMagnet));
                            magnetheld = true;
                        }
                        else
                        {
                            //otherwise

                            //if the pen is on add a new point
                            if(penOn)
                            {
                                points.add(new ArrayList<Point>());
                                points.get(points.size() - 1).add(new Point(x, y));
                            }

                            //check if any magnets are being picked up
                            magnetheld = false;
                            for(int i = 0; i < magnets.size(); i++)
                            {
                                Magnet magnet = magnets.get(i);
                                if(x >= magnet.x && x <= magnet.x + magnet.img.getWidth() && y >= magnet.y && y <= magnet.y + magnet.img.getHeight())
                                {
                                    currentmagnet = i;
                                    magnetheld = true;
                                    xOffset = x - magnet.x;
                                    yOffset = y - magnet.y;
                                }
                            }

                            //check if either of the buttons are being pressed
                            for(int i = 0; i < buttons.size(); i++)
                            {
                                Button button = buttons.get(i);
                                if(x >= button.x && x <= button.x + button.width && y >= button.y && y <= button.y + button.height)
                                {
                                    //if its a toggle button toggle the state
                                    if(button.toggle)
                                    {
                                        buttons.get(i).pushed = !buttons.get(i).pushed;
                                        if(button.name.equals("Pen On/Off"))
                                        {
                                            penOn = buttons.get(i).pushed;
                                        }
                                    }
                                    else
                                    {
                                        //otherwise just set it pushed
                                        buttons.get(i).pushed = true;
                                    }
                                }
                                else if(!button.toggle)
                                {
                                    //if you didn't press a button and its not a toggle depress it
                                    buttons.get(i).pushed = false;
                                }
                            }
                        }
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        //if dragged on

                        //if you are holding a magnet move it
                        if(magnetheld)
                        {
                            magnets.get(currentmagnet).update(x - xOffset, y - yOffset);
                        }

                        //if you are using the pen draw
                        if(penOn && points.size() > 0)
                        {
                            points.get(points.size() - 1).add(new Point(x, y));
                        }
                        return true;
                    case MotionEvent.ACTION_UP:
                        //if let go of

                        //check if you are letting go of a button
                        for(int i = 0; i < buttons.size(); i++)
                        {
                            Button button = buttons.get(i);
                            if(x >= button.x && x <= button.x + button.width && y >= button.y && y <= button.y + button.height && button.pushed && !button.toggle)
                            {
                                //if its the clear button clear all magnets and drawing
                                if(button.name.equals("Clear!"))
                                {
                                    magnets = new ArrayList<>();
                                    points = new ArrayList<>();
                                }
                            }

                            //if its not a toggle depress it
                            if(!button.toggle)
                            {
                                buttons.get(i).pushed = false;
                            }
                        }
                        break;
                }
                return false;
            }
        });
    }

    //this is where everything is drawn
    //it is currently set to be run every time the screen is touched
    @Override
    public void onDraw(Canvas canvas)
    {
        //if the fragment hasn't been initiated initiate it
        if(!run)
        {
            init();
            run = true;
        }
        Paint paint = new Paint();

        //draws a blank canvas
        paint.setColor(Color.WHITE);
        canvas.drawRect(0, 0, getWidth() / 6, getHeight(), paint);

        //draws the field and all the image buttons
        canvas.drawBitmap(field, getWidth() / 6, 0, null);
        canvas.drawBitmap(tote, 10, 10, null);
        canvas.drawBitmap(bin, 10, 20 + tote.getHeight(), null);
        canvas.drawBitmap(coop, 10, 40 + tote.getHeight() + bin.getHeight(), null);
        canvas.drawBitmap(litter, 10, 50 + tote.getHeight() + bin.getHeight() + coop.getHeight(), null);
        canvas.drawBitmap(robot, 10, 60 + tote.getHeight() + bin.getHeight() + coop.getHeight() + litter.getHeight(), null);

        //draws all the buttons
        for(int i = 0; i < buttons.size(); i++)
        {
            buttons.get(i).draw(canvas);
        }

        //draws all the magnets
        for(int i = 0; i < magnets.size(); i++)
        {
            magnets.get(i).draw(canvas);
        }

        //draws the pretty drawing you made
        paint.setColor(Color.RED);
        for(int i = 0; i < points.size(); i++)
        {
            for(int j = 1; j < points.get(i).size(); j++)
            {
                if(j > 0)
                {
                    Point last = points.get(i).get(j-1);
                    Point point = points.get(i).get(j);
                    canvas.drawLine(last.x, last.y, point.x, point.y, paint);
                }
            }
        }
    }

    //this is a object for the magnets that can be moved around
    public class Magnet
    {
        int x, y;
        Bitmap img;

        public Magnet(int x, int y, Bitmap img)
        {
            this.x = x;
            this.y = y;
            this.img = img;
        }

        //updates the position of the magnet
        public void update(int x, int y)
        {
            this.x = x;
            this.y = y;
        }

        //draws the image on the canvas at its position
        public void draw(Canvas c)
        {
            c.drawBitmap(img, x, y, null);
        }
    }

    //this is an object for the custom buttons on the right
    public class Button
    {
        int x, y, width, height;
        String name;
        boolean pushed;
        boolean toggle;

        public Button(int x, int y, int width, int height, String name, boolean toggle)
        {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.name = name;
            this.toggle = toggle;
            pushed = false;
        }

        //draws my custom buttons
        public void draw(Canvas c)
        {
            Paint paint = new Paint();
            //if its pressed it's dark
            if(pushed)
            {
                paint.setColor(Color.DKGRAY);
            }
            else
            {
                //otherwise it's light
                paint.setColor(Color.LTGRAY);
            }
            c.drawRect(x, y, x + width, y + height, paint);
            paint.setColor(Color.BLACK);
            c.drawText(name, x + width/3, y + height/3, paint);
        }
    }

    //this is a object for easily containing an x and y coordinate
    //it's specifically used for drawing
    public class Point
    {
        int x,y;

        public Point(int x, int y)
        {
            this.x = x;
            this.y = y;
        }
    }
}