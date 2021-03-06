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
import com.sun.j3d.loaders.Scene;
import com.sun.j3d.loaders.objectfile.ObjectFile;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;
import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Frame;
import javax.media.j3d.Alpha;
import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.QuadArray;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Texture;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point2f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;


public class Martillo extends Applet{
     SimpleUniverse universo;

    public Martillo() {
        this.y = 0;
    }
     
    @Override
    
    public void init(){
        
        setLayout (new BorderLayout());
        Canvas3D  canvas = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        add("Center",canvas);
        
        universo = new SimpleUniverse(canvas);
        
        BranchGroup scene = createSceneGraph();
        
        TransformGroup tg = universo.getViewingPlatform().
                getViewPlatformTransform();
        Transform3D t3d = new Transform3D();
        t3d.setTranslation(new  Vector3f(0f, 0f, 5.5f));
        
        tg.setTransform(t3d);
        
        scene.compile();
        universo.addBranchGraph(scene);
    }
    
    public BranchGroup createSceneGraph() {
       
        BranchGroup objRoot = new BranchGroup();
        Transform3D posicion = new Transform3D();
        Transform3D escala = new Transform3D();

        escala.set(0.5);
        
        posicion.setTranslation(new Vector3f(0f, 0f, 5f));
            escala.mul(posicion);
            ObjectFile loader = new ObjectFile(ObjectFile.RESIZE);
            Scene s = null;
            try 
            {
               
                String s1 = "10293_Hammer_v1_iterations-2.obj";
                s = loader.load(s1);
                
            } catch (Exception e) {
                System.out.println("error: 1 "+e);
                System.exit(1);
                                               
            }
      
                      
         /*transformgroup tiene asociada una matriz de transformación que afecta 
            a todos los elementos bajo este nodo*/
        TransformGroup objRotate = new TransformGroup(escala);
        TransformGroup objSpin = new TransformGroup();
        objSpin.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        
        objRoot.addChild(objRotate);
        objRotate.addChild(objSpin);
        objSpin.addChild(s.getSceneGroup());
        
        Alpha rotationAlpha= new Alpha (-1,3000);
        RotationInterpolator rotator = new RotationInterpolator (rotationAlpha,objSpin);
        
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
        Texture textImage = new TextureLoader("martillo.jpg", this).getTexture();
        polygon1Appearance.setTexture(textImage);
        objRoot.addChild(new Shape3D(polygon1, polygon1Appearance));
        return objRoot;
    }
    @Override
    public void destroy(){
        universo.removeAllLocales();
    }
    private static double y;
    public static void main(String[] args)
    {
        Transform3D t3d = new Transform3D();
        TransformGroup tg3 = new TransformGroup();
        
        tg3.setTransform(t3d);
        
      Frame frame = new MainFrame(new Martillo(), 780,520);
    }
    
}
