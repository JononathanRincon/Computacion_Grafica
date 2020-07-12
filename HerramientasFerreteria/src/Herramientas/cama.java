/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Herramientas;
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
import javax.media.j3d.ScaleInterpolator;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Texture;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point2f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;
/**
 *
 * @author jonat
 */
public class cama extends Applet {
    SimpleUniverse universo;
    
    @Override
    public void init(){
        setLayout (new BorderLayout());
        Canvas3D  canvas = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        add("Center",canvas);
        
        universo = new SimpleUniverse(canvas);
        
        BranchGroup scene = createSceneGraph();
        
        /*El nodo TransformGroup especifica una única transformación espacial, 
        a través de un objeto Transform3D, que puede posicionar, orientar y escalar
        a todos sus elementos secundarios.*/
        
        TransformGroup tg = universo.getViewingPlatform().getViewPlatformTransform();
        Transform3D t3d = new Transform3D();
        t3d.setTranslation(new  Vector3f(0f, 0f, 5.5f));
        
        tg.setTransform(t3d);
        
        scene.compile();
        universo.addBranchGraph(scene);
    }
        /*Construye e inicializa un nuevo objeto de nodo BranchGroup.*/
      public BranchGroup createSceneGraph() {
       
        BranchGroup objRoot = new BranchGroup();
        Transform3D posicion = new Transform3D();
        Transform3D escala = new Transform3D();

        escala.set(0.5);
        /*en esta parte del codigo verificaremos la posicion del objeto importado*/
        
        posicion.setTranslation(new Vector3f(2.5f, 0.5f, 0.5f));
            escala.mul(posicion);
            ObjectFile loader = new ObjectFile(ObjectFile.RESIZE);
            Scene s = null;
            try 
            {
                String s1 = "10236_Master_Bed_King_Size_v1_L3b.obj";
                s = loader.load(s1);
            } catch (Exception e) {
                System.err.println(e);
                System.exit(1);
            }
      
           /*  Construye e inicializa un TransformGroup usando una transformación de identidad.*/ 
            
        TransformGroup objScale = new TransformGroup(escala);
        TransformGroup objSpin = new TransformGroup();
        objSpin.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        objRoot.addChild(objScale);
        objScale.addChild(objSpin);
        objSpin.addChild(s.getSceneGroup());
        Transform3D ZAxis = new Transform3D();
                Alpha alpha6obj = new Alpha (-1,
                Alpha.INCREASING_ENABLE 
                |Alpha.DECREASING_ENABLE,
                5,
                5,
                1700, 
                200,
                260,
                1700,
                200,
                260);
        ScaleInterpolator escalador  = new ScaleInterpolator(alpha6obj, objSpin, ZAxis, 1f, 1.7f);
        BoundingSphere bounds = new BoundingSphere();
        escalador.setSchedulingBounds(bounds);
        objSpin.addChild(escalador);
        Appearance polygon1Appearance = new  Appearance();
        QuadArray polygon1 = new QuadArray(4, QuadArray.COORDINATES | GeometryArray.TEXTURE_COORDINATE_2);
        polygon1.setCoordinate(0,new Point3f(-3f, -2f, -2f));
        polygon1.setCoordinate(1,new Point3f(3f, -2f, -2f));
        polygon1.setCoordinate(2,new Point3f(3f, 2f, -2f));
        polygon1.setCoordinate(3,new Point3f(-3f, 2f, -2f));
        // muestra la textura del fondo
        polygon1.setTextureCoordinate(0, new Point2f(0.0f, 0.0f));
        polygon1.setTextureCoordinate(1, new Point2f(1.0f, 0.0f));
        polygon1.setTextureCoordinate(2, new Point2f(1.0f, 1.0f));
        polygon1.setTextureCoordinate(3, new Point2f(0.0f, 1.0f));
        Texture textImage = new TextureLoader("cama.jpg", this).getTexture();
        polygon1Appearance.setTexture(textImage);
        objRoot.addChild(new Shape3D(polygon1, polygon1Appearance));
        return objRoot;
    }
    @Override
    public void destroy(){
        universo.removeAllLocales();
    }

    public static void main(String[] args)
    {
      Frame frame = new MainFrame(new cama(), 780,520);
    }
     
}
