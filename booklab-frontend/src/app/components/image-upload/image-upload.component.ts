import {Component, OnInit} from '@angular/core';
import {HttpService} from '../../services/http/http.service';
import {UserService} from '../../services/user/user.service';
import {Book, DetectionResult, Title} from '../../dataTypes';

@Component({
    selector: 'app-image',
    templateUrl: './image-upload.component.html',
    styleUrls: ['./image-upload.component.less']
})

export class ImageUploadComponent implements OnInit {
    public img: any;
    public results: Book[];
    public addedToShelf: boolean;

    constructor(private http: HttpService, private user: UserService) {
    }

    ngOnInit() {
        this.img = null;
        this.results = [];
        this.addedToShelf = false;
    }

    onSubmit(event) {
        console.log('click!');
        const files = event.srcElement.files;
        const reader = new FileReader();
        reader.readAsDataURL(files[0]);
        reader.onload = () => this.img = reader.result;
        this.http.checkHealth();
        this.http.putImg(null).subscribe((res) => {
            this.results = res.results.map(b => Book.getBook(b));
        },
            error => this.http.handleError(error));
        this.addedToShelf = false;

    }

    addToBookShelf(event: Event) {
        console.log('click!');
        this.user.addMultToBookshelf(this.results);
        this.addedToShelf = true;
    }


}