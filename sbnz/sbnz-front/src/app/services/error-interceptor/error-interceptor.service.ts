import { Injectable } from '@angular/core';
import { HttpInterceptor, HTTP_INTERCEPTORS } from '@angular/common/http/src/interceptor';
import { HttpHandler, HttpRequest, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';
import { _throw } from 'rxjs/observable/throw';
import { catchError } from 'rxjs/operators';
import { HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';

@Injectable()
export class ErrorInterceptorService implements HttpInterceptor{

  constructor(private router:Router) { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(req)
        .pipe(catchError((errorResponse => {
            let errMsg: string;
            if (errorResponse instanceof HttpErrorResponse) {
                const err = errorResponse.message || JSON.stringify(errorResponse.error);
                const status=`${errorResponse.status}`;
                console.log(status);
                if(status==="404"){
                  this.router.navigate(['/not-found']);
                }
                errMsg = `${errorResponse.status} - ${errorResponse.statusText || ''} Details: ${err}`;
            } else {
                errMsg = errorResponse.message ? errorResponse.message : errorResponse.toString();
            }
            return _throw(errMsg);
        })));
  }

}
