//
//  ViewController.swift
//  CampusSafari
//
//  Created by Davis Williams on 11/4/19.
//  Copyright © 2019 Team 9323. All rights reserved.
//

import UIKit
import MapKit
import CoreLocation

class ViewController: UIViewController {

    //MARK: Constants
    let regionRadius: CLLocationDistance = 1000
    let coordBoundary = MKCoordinateRegion(center: CLLocationCoordinate2D(latitude: 33.7765, longitude: -84.3983), latitudinalMeters: 939, longitudinalMeters: 1715)
    let locationManager = CLLocationManager()
    
    //MARK: Fields
    @IBOutlet weak var mapView: MKMapView!
    var wildlifes: [Wildlife] =
        [Wildlife(title: "Albino Squirrel", locationName: "DramaTech", level: 10, kingdom: "Animal", coordinate: CLLocationCoordinate2D(latitude: 33.7753, longitude: -84.3989)),
         Wildlife(title: "Magnolia Tree", locationName: "Mickey Mouse Clock", level: 1, kingdom: "Plant", coordinate: CLLocationCoordinate2D(latitude: 33.7737, longitude: -84.3956)),
         Wildlife(title: "Sideways the Dog", locationName: "Uncle Heinie Way", level: 7, kingdom: "Animal", coordinate: CLLocationCoordinate2D(latitude: 33.7724, longitude: -84.3948)),
         Wildlife(title: "Tabby Cat", locationName: "Brittain Dining Hall", level: 2, kingdom: "Animal", coordinate: CLLocationCoordinate2D(latitude: 33.7727, longitude: -84.3914)),
        Wildlife(title: "Monarch Butterfly", locationName: "Boggs Building", level: 4,
                 kingdom: "Animal", coordinate: CLLocationCoordinate2D(latitude: 33.7758, longitude: -84.4001))]
   
    
    
    //MARK: Methods
    override func viewDidLoad() {
        super.viewDidLoad()
        
        checkLocationServices()
        
        // initialize startup loacation, center view on there. Constrain user to within bounds of GT
        let initialLocation = CLLocation(latitude: 33.7765, longitude: -84.3983)
        centerMapOnLocation(location: initialLocation)
        mapView.setCameraBoundary(MKMapView.CameraBoundary(coordinateRegion: coordBoundary), animated: true)
        
        mapView.register(WildlifeMarkerView.self, forAnnotationViewWithReuseIdentifier: MKMapViewDefaultAnnotationViewReuseIdentifier)
        
        //Annotate map with array of wildlife pins
        for w in wildlifes {
            mapView.addAnnotation(w)
        }
        
        
        mapView.delegate = self;
    }
    
    
    @IBAction func centerOnUserLocationOnButtonPress(_ sender: Any) {
        mapView.setUserTrackingMode(MKUserTrackingMode.follow, animated: true)
    }
    
    func checkLocationServices() {
        if CLLocationManager.locationServicesEnabled() {
            setupLocationManager()
            checkLocationAuthorization()
        } else {
            // Show alert
        }
    }
    
    //MARK: Helper Methods
    func centerMapOnLocation(location: CLLocation) {
        let coordinateRegion = MKCoordinateRegion(center: location.coordinate, latitudinalMeters: regionRadius, longitudinalMeters: regionRadius)
        mapView.setRegion(coordinateRegion, animated: true)
    }
    
    func setupLocationManager() {
        locationManager.delegate = self
        locationManager.desiredAccuracy = kCLLocationAccuracyBest
    }
    
//    @objc func centerMapOnUserButtonClicked() {
//        mapView.setUserTrackingMode(MKUserTrackingMode.follow, animated: true)
//    }
//
    func checkLocationAuthorization() {
        switch CLLocationManager.authorizationStatus() {
        case .authorizedWhenInUse:
            mapView.showsUserLocation = true
            break
        case .denied:
            // Show alert instructing how to turn on permissions
            break
        case .notDetermined:
            locationManager.requestWhenInUseAuthorization()
            break
        case .restricted:
            // Show an alert
            break
        case .authorizedAlways:
            break
        @unknown default:
            break
        }
    }
}

extension ViewController: MKMapViewDelegate {
  // 1
//  func mapView(_ mapView: MKMapView, viewFor annotation: MKAnnotation) -> MKAnnotationView? {
//    // 2
//    guard let annotation = annotation as? Wildlife else { return nil }
//    // 3
//    let identifier = "marker"
//    var view: MKMarkerAnnotationView
//    // 4
//    if let dequeuedView = mapView.dequeueReusableAnnotationView(withIdentifier: identifier)
//      as? MKMarkerAnnotationView {
//      dequeuedView.annotation = annotation
//      view = dequeuedView
//    } else {
//      // 5
//      view = MKMarkerAnnotationView(annotation: annotation, reuseIdentifier: identifier)
//      view.canShowCallout = true
//      view.calloutOffset = CGPoint(x: -5, y: 5)
//      view.rightCalloutAccessoryView = UIButton(type: .detailDisclosure)
//    }
//    return view
//  }
}

extension ViewController: CLLocationManagerDelegate {
    
    func locationManager(_ manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
        // Code
    }
    
    func locationManager(_ manager: CLLocationManager, didChangeAuthorization status: CLAuthorizationStatus) {
        // Code
    }
}
