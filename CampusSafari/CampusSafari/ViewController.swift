//
//  ViewController.swift
//  CampusSafari
//
//  Created by Davis Williams on 11/4/19.
//  Copyright © 2019 Team 9323. All rights reserved.
//

import UIKit
import MapKit

class ViewController: UIViewController {

    //MARK: Constants
    let regionRadius: CLLocationDistance = 1000
    let coordBoundary = MKCoordinateRegion(center: CLLocationCoordinate2D(latitude: 33.7765, longitude: -84.3983), latitudinalMeters: 939, longitudinalMeters: 1715)
    
    //MARK: Fields
    @IBOutlet weak var mapView: MKMapView!
    
    
    //MARK: Methods
    override func viewDidLoad() {
        super.viewDidLoad()
        // initialize startup loacation, center view on there. Constrain user to within bounds of GT
        let initialLocation = CLLocation(latitude: 33.7765, longitude: -84.3983)
        centerMapOnLocation(location: initialLocation)
        mapView.setCameraBoundary(MKMapView.CameraBoundary(coordinateRegion: coordBoundary), animated: true)
        
        //Make an annotation
        let albinoSquirrel = Wildlife(title: "Albino Squirrel", locationName: "DramaTech", level: "Level 100", coordinate: CLLocationCoordinate2D(latitude: 33.7753, longitude: -84.3989))
        mapView.addAnnotation(albinoSquirrel)
        
        mapView.delegate = self;
    }
    
    
    //MARK: Helper Methods
    func centerMapOnLocation(location: CLLocation) {
        let coordinateRegion = MKCoordinateRegion(center: location.coordinate, latitudinalMeters: regionRadius, longitudinalMeters: regionRadius)
        mapView.setRegion(coordinateRegion, animated: true)
    }
}

extension ViewController: MKMapViewDelegate {
  // 1
  func mapView(_ mapView: MKMapView, viewFor annotation: MKAnnotation) -> MKAnnotationView? {
    // 2
    guard let annotation = annotation as? Wildlife else { return nil }
    // 3
    let identifier = "marker"
    var view: MKMarkerAnnotationView
    // 4
    if let dequeuedView = mapView.dequeueReusableAnnotationView(withIdentifier: identifier)
      as? MKMarkerAnnotationView {
      dequeuedView.annotation = annotation
      view = dequeuedView
    } else {
      // 5
      view = MKMarkerAnnotationView(annotation: annotation, reuseIdentifier: identifier)
      view.canShowCallout = true
      view.calloutOffset = CGPoint(x: -5, y: 5)
      view.rightCalloutAccessoryView = UIButton(type: .detailDisclosure)
    }
    return view
  }
}

