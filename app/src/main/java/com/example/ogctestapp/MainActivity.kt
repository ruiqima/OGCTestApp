package com.example.ogctestapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.arcgismaps.ApiKey
import com.arcgismaps.ArcGISEnvironment
import com.arcgismaps.Color
import com.arcgismaps.geometry.Point
import com.arcgismaps.geometry.SpatialReference
import com.arcgismaps.mapping.ArcGISScene
import com.arcgismaps.mapping.ArcGISTiledElevationSource
import com.arcgismaps.mapping.layers.Ogc3DTilesLayer
import com.arcgismaps.mapping.symbology.SceneSymbolAnchorPosition
import com.arcgismaps.mapping.symbology.SimpleMarkerSceneSymbol
import com.arcgismaps.mapping.symbology.SimpleMarkerSceneSymbolStyle
import com.arcgismaps.mapping.view.Camera
import com.arcgismaps.mapping.view.Graphic
import com.arcgismaps.mapping.view.GraphicsOverlay
import com.arcgismaps.mapping.view.SceneView
import com.arcgismaps.mapping.view.SurfacePlacement

class MainActivity : ComponentActivity() {

    private lateinit var sceneView : SceneView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ArcGISEnvironment.apiKey = ApiKey.create("YOUR_API_KEY")

        sceneView = findViewById(R.id.sceneView)

        val scene = ArcGISScene().apply {
            baseSurface.elevationSources.add(
                ArcGISTiledElevationSource("https://elevation3d.arcgis.com/arcgis/rest/services/WorldElevation3D/Terrain3D/ImageServer")
            )
        }
        val tiles3dPath = getExternalFilesDir(null)?.path + "/Gravel_3d_mesh.json"
        val ogc3DTilesLayer = Ogc3DTilesLayer(tiles3dPath)
        scene.operationalLayers.add(ogc3DTilesLayer)
        sceneView.scene = scene

        // adds three graphics to the scene view to verify the 3D tiles layer is placed correctly.
        val graphicsOverlay = GraphicsOverlay().apply {
            sceneProperties.surfacePlacement = SurfacePlacement.Absolute
        }
        val road1 = Point(8.02385, 46.3411, 712.613, SpatialReference.wgs84())
        val road2 = Point(8.02567, 46.3429, 718.523, SpatialReference.wgs84())
        val railroad1 = Point(8.02542, 46.3407, 712.205, SpatialReference.wgs84())
        val simpleMarkerSceneSymbol = SimpleMarkerSceneSymbol(
            SimpleMarkerSceneSymbolStyle.Diamond,
            Color.green,
            10.0,
            10.0,
            10.0,
            SceneSymbolAnchorPosition.Center
        )
        with(graphicsOverlay.graphics) {
            add(Graphic(road1, simpleMarkerSceneSymbol))
            add(Graphic(road2, simpleMarkerSceneSymbol))
            add(Graphic(railroad1, simpleMarkerSceneSymbol))
        }
        sceneView.graphicsOverlays.add(graphicsOverlay)

        // the camera is modified to fit the Android device view.
        val camera = Camera(
            Point(8.021548309454653, 46.33754563822651, 945.7098963772878,
            SpatialReference.wgs84()), 31.998409907511512 , 68.28104607356202, 0.0)
        sceneView.setViewpointCamera(camera)

    }
}
