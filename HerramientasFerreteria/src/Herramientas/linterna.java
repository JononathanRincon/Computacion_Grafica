/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Herramientas;

/**
 *
 * @author jonat
 */
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;
import java.applet.Applet;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3f;
import java.awt.Frame;
import com.sun.j3d.loaders.Scene;
import com.sun.j3d.loaders.objectfile.ObjectFile;
import com.sun.j3d.utils.applet.MainFrame;
import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import javax.media.j3d.*;
import javax.vecmath.*;

public class linterna extends Applet {
    
   TransformGroup escenaTransform = new TransformGroup();
    Transform3D t3d = new Transform3D();
    
   
    SimpleUniverse universo = null;
    @Override
    public void init (){
                
        setLayout (new BorderLayout());
        Canvas3D  canvas = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        add("Center",canvas);
        universo = new SimpleUniverse(canvas);

        BranchGroup escena = cargarEscena();
        escenaTransform = universo.getViewingPlatform().getViewPlatformTransform();
        t3d.setTranslation(new  Vector3f(0f, 0f, 5.5f));
        escenaTransform.setTransform(t3d);
        
        escena.compile();
        universo.addBranchGraph(escena);
                   
    }
    private Transform3D t3dstep = new Transform3D();
    private Matrix4d matrix = new Matrix4d();
    @Override
    public void destroy(){
        universo.removeAllLocales();
    }
    private BranchGroup cargarEscena(){
        BranchGroup objRoot = new BranchGroup();
        Transform3D posicion = new Transform3D();
        Transform3D escala = new Transform3D();

        escala.set(0.5);
        
        posicion.setTranslation(new Vector3f(2f, 0.05f, 0f));
            escala.mul(posicion);
            ObjectFile loader = new ObjectFile(ObjectFile.RESIZE);
            Scene s = null;
        
        try 
        {
            String s1 = "flashlight.obj" ;
            s = loader.load(s1);

        } catch (Exception e) {
            System.out.println("error: " + e);
            System.exit(1);
        }
        TransformGroup objRotate = new TransformGroup(escala);
        TransformGroup objSpin = new TransformGroup();
        objSpin.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        objRoot.addChild(objRotate);
        objRotate.addChild(objSpin);
        objSpin.addChild(s.getSceneGroup());        
          
        Alpha rotationAlpha= new Alpha (-1,2000);
        ScaleInterpolator rotator = new ScaleInterpolator (rotationAlpha,objSpin);
        
        BoundingSphere bounds =new BoundingSphere();
        rotator.setSchedulingBounds(bounds);
        objSpin.addChild(rotator);
           
           Appearance polygon1Appearance= new Appearance();
           QuadArray polygon1 = new QuadArray(4, QuadArray.COORDINATES |
                GeometryArray.TEXTURE_COORDINATE_2);
        polygon1.setCoordinate(0,new Point3f(-3f, -2f, -2f));
        polygon1.setCoordinate(1,new Point3f(3f, -2f, -2f));
        polygon1.setCoordinate(2,new Point3f(3f, 2f, -2f));
        polygon1.setCoordinate(3,new Point3f(-3f, 2f, -2f));
        
        polygon1.setTextureCoordinate(0, new Point2f(0.0f, 0.0f));
        polygon1.setTextureCoordinate(1, new Point2f(1.0f, 0.0f));
        polygon1.setTextureCoordinate(2, new Point2f(1.0f, 1.0f));
        polygon1.setTextureCoordinate(3, new Point2f(0.0f, 1.0f));
        Texture textImage = new TextureLoader("linterna.jpg", this).getTexture();
        polygon1Appearance.setTexture(textImage);
        objRoot.addChild(new Shape3D(polygon1, polygon1Appearance));
        return objRoot;
    }

    
    
    public static void main(String[] args) {
       Transform3D t3d = new Transform3D();
        TransformGroup tg3 = new TransformGroup();
        
        tg3.setTransform(t3d);
        
      Frame frame = new MainFrame(new linterna(), 780,520);
        
    }

    
    
    
}
